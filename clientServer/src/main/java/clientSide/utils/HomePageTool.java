package clientSide.utils;

import clientSide.dto.JobTitle;
import clientSide.services.PostDao;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class HomePageTool {

    public static void sethomePageModel(String name, ModelAndView modelAndView){
        if(name.equals("anonymousUser")) {
            modelAndView.addObject("profileButton","hide");
            return;
        }

        modelAndView.addObject("userActive","active");
        modelAndView.addObject("parameter", name);
        return;
    }

    public static List<JobTitle> getJobTitles(HttpServletRequest req,PostDao postDao,ModelAndView modelAndView,
                                    String name){

        List<JobTitle> jobTitles = (List<JobTitle>) req.getSession().getAttribute("jobTitles");
        if( jobTitles==null){
            jobTitles= postDao.getJobTitles(name);
        }
        modelAndView.addObject("jobTitles", jobTitles);
        return jobTitles;
    }

    public static List<JobTitle> getJobTitles(HttpServletRequest req,PostDao postDao,ModelAndView modelAndView){

        List<JobTitle> jobTitles = (List<JobTitle>) req.getSession().getAttribute("jobTitles");
        if( jobTitles==null){
            jobTitles = postDao.getJobTitles();
        }
        modelAndView.addObject("jobTitles", jobTitles);
        return jobTitles;
    }
}
