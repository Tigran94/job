package clientSide.controllers;

import clientSide.dao.PostDao;
import clientSide.dto.JobTitle;
import clientSide.entities.Post;
import clientSide.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("jobTitles",postDao.getJobTitles(authentication.getName()));
        modelMap.addAttribute("hiddenContent","hidden");
        return "profile";
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public ModelAndView getJobById(@PathVariable("jobId") long id, HttpServletRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("profile");
        List<JobTitle> jobTitles;
        if( req.getSession().getAttribute("jobTitles")==null){
            jobTitles= postDao.getJobTitles(authentication.getName());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("hiddenContent","hidden");

        modelAndView.addObject("jobTitles",postDao.getJobTitles(type,salary,workTime,authentication));
        req.getSession().setAttribute("jobTitles",postDao.getJobTitles(type,salary,workTime,authentication));
        postDao.getJobTitles(type,salary,workTime,authentication);
        return modelAndView;
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public String deletePost(@RequestParam("jobId") long id){
        postDao.deletePost(id);
        return "redirect:/profile";
    }
}