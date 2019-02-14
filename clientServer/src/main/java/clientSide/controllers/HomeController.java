package clientSide.controllers;

import clientSide.dto.JobTitle;
import clientSide.entities.PostEntity;
import clientSide.services.PostService;
import clientSide.utils.HomePageTool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController{
    private final PostService postService;
    private final JavaMailSender mailSender;
    private final String storage = "usersCV";


    public HomeController(PostService postService, JavaMailSender mailSender) {
        this.postService = postService;
        this.mailSender = mailSender;
    }

    @GetMapping
    public ModelAndView homeString(HttpServletRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<JobTitle> jobTitles;

        ModelAndView modelAndView = new ModelAndView("home");
        if(authentication.getAuthorities().toString().contains("ROLE_ADMIN")){
            jobTitles = postService.getJobTitlesWithoutCompany(authentication.getName());
        }else{
            jobTitles = postService.getJobTitles();
        }
        req.getSession().setAttribute("jobTitles",jobTitles);
        modelAndView.addObject("jobTitles",jobTitles);
        return modelAndView;
    }

    @GetMapping("/reset")
    public String resetFilter(HttpServletRequest req){
        req.getSession().setAttribute("jobTitles",postService.getJobTitles());
        return "redirect:/";
    }

    @PostMapping
    public ModelAndView homeFiltered(HttpServletRequest req,
                             @RequestParam("type") String type,
                             @RequestParam("workTime") String workTime,
                             @RequestParam("salary") String salary){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("home");
        List<JobTitle> jobTitles;

        if(authentication.getAuthorities().toString().contains("ROLE_ADMIN")){
            jobTitles = postService.getJobTitlesWithoutCompany(type,salary,workTime,authentication);
        }else{
            jobTitles = postService.getJobTitles(type,salary,workTime);
        }
        if(jobTitles.size()==0){
            modelAndView.addObject("noJobTitlesFound","No job titles found with that criteria");
        }
        modelAndView.addObject("jobTitles",jobTitles);
        req.getSession().setAttribute("jobTitles",jobTitles);
        return modelAndView;
    }

    @PostMapping(value = "/apply")
    public String applyPost(@RequestParam("jobId") long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PostEntity postEntity = postService.getJobAnnouncementByIdWithStream(id);
        String path = storage + File.separator + authentication.getName();
        path = new File(path).getAbsolutePath();
        File userFolder = new File(path);
        File userCv = new File(path + File.separator + userFolder.list()[0]);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject("Job Application");
            helper.setText("Hi, this cv is coming from job.am, please pay attention to him\n"
                    + "My name is " +authentication.getName() + " and i really need this job");
            helper.setTo(postEntity.getEmail());
            helper.setFrom("springtest94@gmail.com");
            helper.addAttachment(userCv.getName(), userCv);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
        return "redirect:/";
    }

    @GetMapping(value = "/{jobId}")
    public ModelAndView getJobById(@PathVariable("jobId") long id, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("contentView","hidden");
        HomePageTool.getJobTitles(req, postService,modelAndView);
        modelAndView.addObject("post", postService.getJobAnnouncementByIdWithStream(id));
        return modelAndView;
    }
}
