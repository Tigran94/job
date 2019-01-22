package clientSide.controllers;


import clientSide.dao.PostDao;
import clientSide.dto.JobTitle;
import clientSide.entities.Post;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import clientSide.entities.User;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/home")
public class HomeController{
    private final PostDao postDao;


    public HomeController(PostDao postDao) {
        this.postDao = postDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String homeString(ModelMap modelMap,HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        if(user==null) {
            user = new User();
            user.setUsername("Anonymous");
            return "redirect:/guest";
        }else{
            user.setUsername(user.getUsername());
        }
        modelMap.addAttribute("parameter", user.getUsername());
        modelMap.addAttribute("jobTitles",postDao.getJobTitlesStream());
        return "home";
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public ModelAndView getJobById(@PathVariable("jobId") long id,HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("posts");
        List<JobTitle> jobTitles;
        if( req.getSession().getAttribute("jobTitles")==null){
            jobTitles= postDao.getJobTitlesStream();
        }else {
            jobTitles = (List<JobTitle>) req.getSession().getAttribute("jobTitles");
        }
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
        modelAndView.addObject("parameter", user.getUsername());
        modelAndView.addObject("jobTitles",postDao.getJobTitlesStream(type,salary,workTime));
        req.getSession().setAttribute("jobTitles",postDao.getJobTitlesStream(type,salary,workTime));
        postDao.getJobTitlesStream(type,salary,workTime);
        return modelAndView;
    }
}
