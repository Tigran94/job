package clientSide.utils;

import clientSide.dto.JobTitle;
import clientSide.services.PostService;

import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HomePageTool {


    public static List<JobTitle> getJobTitles(HttpServletRequest req, PostService postService, ModelAndView modelAndView,String name){
        List<JobTitle> jobTitles = (List<JobTitle>) req.getSession().getAttribute("jobTitles");
        if( jobTitles==null){
            jobTitles= postService.getJobTitlesForComapny(name);
        }
        modelAndView.addObject("jobTitles", jobTitles);
        return jobTitles;
    }

    public static List<JobTitle> getJobTitles(HttpServletRequest req, PostService postService, ModelAndView modelAndView){
        List<JobTitle> jobTitles = (List<JobTitle>) req.getSession().getAttribute("jobTitles");
        if( jobTitles==null){
            jobTitles = postService.getJobTitles();
        }
        modelAndView.addObject("jobTitles", jobTitles);
        return jobTitles;
    }
}
