package clientSide.controllers;

import clientSide.services.PostDao;
import clientSide.dto.JobTitle;
import clientSide.utils.HomePageTool;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/{jobId}")
public class JobsController {

    private final PostDao postDao;

    public JobsController(PostDao postDao) {
        this.postDao = postDao;
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public ModelAndView getUserJobById(@PathVariable("jobId") long id, HttpServletRequest req) {
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

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getJobById(@PathVariable("jobId") long id, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("posts");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<JobTitle> jobTitles = HomePageTool.getJobTitles(req,postDao,modelAndView);
        HomePageTool.sethomePageModel(authentication.getName(),modelAndView);
        modelAndView.addObject("jobTitles", jobTitles);
        modelAndView.addObject("post", postDao.getJobAnnouncementByIdWithStream(id));
        return modelAndView;
    }


}
