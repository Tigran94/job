package clientSide.controllers;

import clientSide.services.PostService;
import clientSide.entities.PostEntity;

import clientSide.utils.HomePageTool;
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

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final PostService postService;
    public ProfileController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String profileString(HttpServletRequest req, ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().toString().contains("ROLE_USER")){
            return "redirect:/";
        }
        modelMap.addAttribute("jobTitles", postService.getJobTitlesForComapny(authentication.getName()));
        modelMap.addAttribute("hiddenContent","hidden");
        modelMap.addAttribute("post",new PostEntity());
        modelMap.addAttribute("hidenValue","hidden");
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

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView filteredJobs(HttpServletRequest req,
                                   @RequestParam("type") String type,
                                   @RequestParam("workTime") String workTime,
                                   @RequestParam("salary") String salary){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("hiddenContent","hidden");

        modelAndView.addObject("jobTitles", postService.getJobTitlesForCompany(type,salary,workTime,authentication));
        req.getSession().setAttribute("jobTitles", postService.getJobTitlesForCompany(type,salary,workTime,authentication));
        postService.getJobTitlesForCompany(type,salary,workTime,authentication);
        return modelAndView;
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public String deletePost(@RequestParam("jobId") long id){
        postService.deletePost(id);
        return "redirect:/profile";
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public ModelAndView getUserJobById(@PathVariable("jobId") long id, HttpServletRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("contentView","hidden");
        HomePageTool.
                getJobTitles(req, postService,modelAndView,authentication.getName());
        modelAndView.addObject("post", postService.getJobAnnouncementByIdWithStream(id));
        modelAndView.addObject("selected","selected");
        return modelAndView;
    }
}