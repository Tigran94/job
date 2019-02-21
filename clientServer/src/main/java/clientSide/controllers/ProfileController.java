package clientSide.controllers;

import clientSide.dto.JobTitle;
import clientSide.dto.PostDto;
import clientSide.dto.PostSearchDto;
import clientSide.entities.CompanyEntity;
import clientSide.entities.CompanyHistory;
import clientSide.search.PostSearch;
import clientSide.services.CompanyHistoryService;
import clientSide.services.PostService;
import clientSide.entities.PostEntity;

import clientSide.utils.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final PostService postService;
    private final CompanyHistoryService companyHistoryService;
    public ProfileController(PostService postService,CompanyHistoryService companyHistoryService) {
        this.companyHistoryService = companyHistoryService;
        this.postService = postService;
    }

    @GetMapping
    public String profileString(ModelMap modelMap){
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

    @GetMapping(value = "/")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @PostMapping
    public ModelAndView filteredJobs(PostSearchDto postSearchDto){
        PostSearch postSearch = new PostSearch(postSearchDto.getType(),postSearchDto.getSalary(),postSearchDto.getWorkTime());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("/profile");
        List<JobTitle> jobTitles = postService.getJobTitlesForCompany(postSearch,authentication.getName());
        if(jobTitles.isEmpty()){
            modelAndView.addObject("noJobTitlesFound","No job titles found by that criteria");
            return modelAndView;
        }
        modelAndView.addObject("jobTitles",jobTitles);

       return modelAndView;
    }

    @PostMapping(value = "/del")
    public String deletePost(@RequestParam("jobId") long id){
        PostEntity postEntity = postService.deletePost(id);

        CompanyEntity companyEntity = postEntity.getUser();
        CompanyHistory companyHistory = companyHistoryService.convertCompanyToHistory(companyEntity);
        companyHistory.setAction("Deleted " + "\""+postEntity.getTitle()+"\""+" post" + "at" +
                DateUtils.getCurrentTime());
        companyHistoryService.save(companyHistory);

        return "redirect:/profile";
    }

    @GetMapping(value = "/{jobId}")
    public ResponseEntity getUserJobById(@PathVariable("jobId") long id) {
        PostDto postDto = new PostDto(postService.getJobAnnouncementByIdWithStream(id));
        return ResponseEntity.ok(postDto);
    }
}