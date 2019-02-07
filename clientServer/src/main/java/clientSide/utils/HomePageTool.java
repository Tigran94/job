package clientSide.utils;

import clientSide.dto.JobTitle;
import clientSide.services.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HomePageTool {

    public static void sethomePageModel(Authentication authUser, ModelAndView modelAndView, PostService postService){

        if(authUser.getName().equals("anonymousUser")) {
            modelAndView.addObject("profileButton","hide");
            modelAndView.addObject("settingsButton","hide");
            modelAndView.addObject("jobTitles", postService.getJobTitles());
            return;
        }else if(authUser.getAuthorities().toString().contains("ROLE_USER")){
            modelAndView.addObject("profileButton","hide");
            modelAndView.addObject("jobTitles", postService.getJobTitles());

        }else{
            modelAndView.addObject("settingsButton","hide");
            modelAndView.addObject("jobTitles", postService.getJobTitlesWithoutCompany(authUser.getName()));
        }
        modelAndView.addObject("userActive","active");
        modelAndView.addObject("parameter", authUser.getName());

        return;
    }
    public static void sethomePageModel(Authentication authUser, ModelAndView modelAndView){

        if(authUser.getName().equals("anonymousUser")) {
            modelAndView.addObject("profileButton","hide");
            modelAndView.addObject("settingsButton","hide");
            return;
        }else if(authUser.getAuthorities().toString().contains("ROLE_USER")){
            modelAndView.addObject("profileButton","hide");

        }else{
            modelAndView.addObject("settingsButton","hide");
        }
        modelAndView.addObject("userActive","active");
        modelAndView.addObject("parameter", authUser.getName());

        return;
    }

    public static List<JobTitle> getJobTitles(HttpServletRequest req, PostService postService, ModelAndView modelAndView,
                                              String name){

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
