package clientSide.controllers;


import clientSide.dao.PostDao;
import clientSide.dto.JobTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import clientSide.entities.User;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/home")
public class HomeController{
    private final PostDao postDao;

    @Autowired
    private JavaMailSender mailSender;

    public HomeController(PostDao postDao) {
        this.postDao = postDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String homeString(ModelMap modelMap,HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        if(user==null || user.getUsername().equals("Anonymous")) {
            user = new User();
            user.setUsername("Anonymous");
            modelMap.addAttribute("hiddenButton","hidden");
        }else{
            user.setUsername(user.getUsername());
            modelMap.addAttribute("hiddenBack","hidden");

        }

        modelMap.addAttribute("parameter", user.getUsername());
        modelMap.addAttribute("jobTitles",postDao.getJobTitles());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("tiktor19941994@gmail.com");
        email.setSubject("asdasd");
        email.setText("asdjasd");

        // sends the e-mail
        mailSender.send(email);


        return "home";
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public ModelAndView getJobById(@PathVariable("jobId") long id,HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("posts");
        User user = (User) req.getSession().getAttribute("user");
        List<JobTitle> jobTitles;
        if( req.getSession().getAttribute("jobTitles")==null){
            jobTitles= postDao.getJobTitles();
        }else {
            jobTitles = (List<JobTitle>) req.getSession().getAttribute("jobTitles");
        }
        if(user==null || user.getUsername().equals("Anonymous")) {
            user = new User();
            user.setUsername("Anonymous");
            modelAndView.addObject("hiddenButton","hidden");
        }else{
            user.setUsername(user.getUsername());
            modelAndView.addObject("hiddenBack","hidden");
        }
        modelAndView.addObject("parameter", user.getUsername());
        modelAndView.addObject("jobTitles", jobTitles);
        modelAndView.addObject("post", postDao.getJobAnnouncementByIdWithStream(id));
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView homeString(HttpServletRequest req,
                             @RequestParam("type") String type,
                             @RequestParam("workTime") String workTime,
                             @RequestParam("salary") String salary){
        User user = (User) req.getSession().getAttribute("user");
        ModelAndView modelAndView = new ModelAndView("home");
        if(user==null || user.getUsername().equals("Anonymous")) {
            user = new User();
            user.setUsername("Anonymous");
            modelAndView.addObject("hiddenButton","hidden");
        }else{
            user.setUsername(user.getUsername());
            modelAndView.addObject("hiddenBack","hidden");

        }
        modelAndView.addObject("parameter", user.getUsername());
        modelAndView.addObject("jobTitles",postDao.getJobTitles(type,salary,workTime));
        req.getSession().setAttribute("jobTitles",postDao.getJobTitles(type,salary,workTime));
        postDao.getJobTitles(type,salary,workTime);
        return modelAndView;
    }
}
