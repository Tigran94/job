package clientSide.controllers;

import clientSide.dao.PostDao;
import clientSide.dto.JobTitle;
import clientSide.entities.Post;
import clientSide.entities.User;
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
@RequestMapping("/profile")
public class ProfileController {

    private final PostDao postDao;
    public ProfileController(PostDao postDao) {
        this.postDao = postDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String profileString(HttpServletRequest req, ModelMap modelMap){

        User user =(User)req.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/home";
        }
        modelMap.addAttribute("jobTitles",postDao.getJobTitles(user.getEmail()));
        modelMap.addAttribute("hiddenContent","hidden");
        return "profile";
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String logOutString(HttpServletRequest req){
        req.getSession().setAttribute("user",null);
        return "redirect:/";
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public ModelAndView getJobById(@PathVariable("jobId") long id, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("profile");
        User user = (User) req.getSession().getAttribute("user");
        List<JobTitle> jobTitles;
        if( req.getSession().getAttribute("jobTitles")==null){
            jobTitles= postDao.getJobTitles(user.getEmail());
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
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("hiddenContent","hidden");

        modelAndView.addObject("jobTitles",postDao.getJobTitles(type,salary,workTime,user.getEmail()));
        req.getSession().setAttribute("jobTitles",postDao.getJobTitles(type,salary,workTime,user.getEmail()));
        postDao.getJobTitles(type,salary,workTime,user.getEmail());
        return modelAndView;
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public String deletePost(@RequestParam("jobId") long id){
        postDao.deletePost(id);
        return "redirect:/profile";
    }
}