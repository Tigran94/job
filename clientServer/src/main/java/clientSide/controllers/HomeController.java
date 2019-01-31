package clientSide.controllers;


import clientSide.dto.JobTitle;
import clientSide.services.PostDao;
import clientSide.utils.HomePageTool;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController{
    private final PostDao postDao;

//    @Autowired
//    private JavaMailSender mailSender;

    public HomeController(PostDao postDao) {
        this.postDao = postDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView homeString(HttpServletRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ModelAndView modelAndView = new ModelAndView("home");

        HomePageTool.sethomePageModel(authentication.getName(),modelAndView);
        modelAndView.addObject("jobTitles",postDao.getJobTitles());

//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo("tiktor19941994@gmail.com");
//        email.setSubject("asdasdad");
//        email.setText("asdjasd");
//
//        // sends the e-mail
//        mailSender.send(email);


        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView homeFiltered(HttpServletRequest req,
                             @RequestParam("type") String type,
                             @RequestParam("workTime") String workTime,
                             @RequestParam("salary") String salary){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("home");

        HomePageTool.sethomePageModel(authentication.getName(),modelAndView);
        List<JobTitle> jobTitles = postDao.getJobTitles(type,salary,workTime);
        modelAndView.addObject("jobTitles",jobTitles);
        req.getSession().setAttribute("jobTitles",jobTitles);
        return modelAndView;
    }
}
