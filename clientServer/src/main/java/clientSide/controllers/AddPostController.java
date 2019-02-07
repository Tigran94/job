package clientSide.controllers;

import clientSide.services.PostService;
import clientSide.entities.PostEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
@RequestMapping("/addPost")
public class AddPostController {

    private final PostService postService;

    public AddPostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String addPostString(){
        return "addPost";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPost(PostEntity postEntity, HttpServletRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String[] date = req.getParameter("endDate").split("-");

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        postEntity.setPostDate(new Date());
        postEntity.setExpirationDate(new GregorianCalendar(year,month,day).getTime());

        postService.addPost(postEntity,authentication);

        return "redirect:/";
    }
}
