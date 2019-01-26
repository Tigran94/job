package clientSide.controllers;


import clientSide.dao.PostDao;
import clientSide.dto.JobTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController{
    private final PostDao postDao;

    @Autowired
    private JavaMailSender mailSender;

    public HomeController(PostDao postDao) {
        this.postDao = postDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String homeString(ModelMap modelMap,HttpServletRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")) {
            modelMap.addAttribute("hiddenButton","hidden");
        } else{
            modelMap.addAttribute("hiddenLogin","hidden");
            modelMap.addAttribute("hiddenSignUp","hidden");
        }

        modelMap.addAttribute("parameter", authentication.getName());
        modelMap.addAttribute("jobTitles",postDao.getJobTitles());

//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo("tiktor19941994@gmail.com");
//        email.setSubject("asdasdad");
//        email.setText("asdjasd");
//
//        // sends the e-mail
//        mailSender.send(email);


        return "home";
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public ModelAndView getJobById(@PathVariable("jobId") long id,HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("posts");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<JobTitle> jobTitles;
        if( req.getSession().getAttribute("jobTitles")==null){
            jobTitles= postDao.getJobTitles();
        }else {
            jobTitles = (List<JobTitle>) req.getSession().getAttribute("jobTitles");
        }
        if(authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("hiddenButton","hidden");
        } else{
            modelAndView.addObject("hiddenLogin","hidden");
            modelAndView.addObject("hiddenSignUp","hidden");

        }
        modelAndView.addObject("parameter", authentication.getName());
        modelAndView.addObject("jobTitles", jobTitles);
        modelAndView.addObject("post", postDao.getJobAnnouncementByIdWithStream(id));
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView homeString(HttpServletRequest req,
                             @RequestParam("type") String type,
                             @RequestParam("workTime") String workTime,
                             @RequestParam("salary") String salary){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("home");

        if(authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("hiddenButton","hidden");
        } else{
            modelAndView.addObject("hiddenLogin","hidden");
            modelAndView.addObject("hiddenSignUp","hidden");
        }
        modelAndView.addObject("parameter", authentication.getName());
        modelAndView.addObject("jobTitles",postDao.getJobTitles(type,salary,workTime));
        req.getSession().setAttribute("jobTitles",postDao.getJobTitles(type,salary,workTime));
        postDao.getJobTitles(type,salary,workTime);
        return modelAndView;
    }
}
