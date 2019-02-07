package clientSide.controllers;


import clientSide.dto.JobTitle;
import clientSide.entities.Post;
import clientSide.services.PostDao;
import clientSide.utils.HomePageTool;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController{
    private final PostDao postDao;
    private final JavaMailSender mailSender;

    public HomeController(PostDao postDao, JavaMailSender mailSender) {
        this.postDao = postDao;
        this.mailSender = mailSender;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView homeString(HttpServletRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ModelAndView modelAndView = new ModelAndView("home");

        HomePageTool.sethomePageModel(authentication,modelAndView,postDao);

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView homeFiltered(HttpServletRequest req,
                             @RequestParam("type") String type,
                             @RequestParam("workTime") String workTime,
                             @RequestParam("salary") String salary){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("home");

        HomePageTool.sethomePageModel(authentication,modelAndView);
        if(authentication.getAuthorities().toString().contains("ROLE_ADMIN")){
            List<JobTitle> jobTitles = postDao.getJobTitlesWithoutCompany(type,salary,workTime,authentication);
            modelAndView.addObject("jobTitles",jobTitles);
            req.getSession().setAttribute("jobTitles",jobTitles);
            return modelAndView;
        }
        List<JobTitle> jobTitles = postDao.getJobTitles(type,salary,workTime);
        modelAndView.addObject("jobTitles",jobTitles);
        req.getSession().setAttribute("jobTitles",jobTitles);
        return modelAndView;
    }

    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    public String applyPost(@RequestParam("jobId") long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = postDao.getJobAnnouncementByIdWithStream(id);
        File file;
        if(new File(new ClassPathResource(File.separator+"usersCV"+File.separator + authentication.getName()+File.separator+"cv.pdf").getPath()).exists()){
            file = new File(new ClassPathResource(File.separator+"usersCV"+File.separator + authentication.getName()+File.separator+"cv.pdf").getPath());
        }else   file = new File(new ClassPathResource(File.separator+"usersCV"+File.separator + authentication.getName()+File.separator+"cv.docx").getPath());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject("Job Application");
            helper.setText("Hi, this cv is coming from job.am, please pay attention to him\n"
                    + "My name is " +authentication.getName() + " and i really need this job");
            helper.setTo(post.getEmail());
            helper.setFrom("springtest94@gmail.com");
            helper.addAttachment("cv.pdf", file);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
        return "redirect:/";
    }
    @RequestMapping(value = "/{jobId}" ,method = RequestMethod.GET)
    public ModelAndView getJobById(@PathVariable("jobId") long id, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("home");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("contentView","hidden");
        HomePageTool.getJobTitles(req,postDao,modelAndView);
        HomePageTool.sethomePageModel(authentication,modelAndView,postDao);
        modelAndView.addObject("post", postDao.getJobAnnouncementByIdWithStream(id));
        return modelAndView;
    }
}
