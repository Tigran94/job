package clientSide.controllers;

import clientSide.entities.CompanyEntity;
import clientSide.entities.CompanyHistory;
import clientSide.services.CompanyHistoryService;
import clientSide.services.PostService;
import clientSide.entities.PostEntity;

import clientSide.utils.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
@RequestMapping("/addPost")
public class AddPostController {

    private final PostService postService;
    private final CompanyHistoryService companyHistoryService;

    public AddPostController(PostService postService,CompanyHistoryService companyHistoryService) {
        this.companyHistoryService = companyHistoryService;
        this.postService = postService;
    }

    @GetMapping
    public String addPostString(){

        return "addPost";
    }

    @PostMapping
    public String addPost(PostEntity postEntity, HttpServletRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String[] date = req.getParameter("endDate").split("-");

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        postEntity.setPostDate(new Date());
        postEntity.setExpirationDate(new GregorianCalendar(year,month,day).getTime());

        CompanyEntity companyEntity = postService.addPost(postEntity,authentication).getUser();

        CompanyHistory companyHistory = companyHistoryService.convertCompanyToHistory(companyEntity);
        companyHistory.setAction("I have added " + "\""+postEntity.getTitle()+"\""+" post" +
                "at" + DateUtils.getCurrentTime());
        companyHistoryService.save(companyHistory);

        return "redirect:/";
    }
}
