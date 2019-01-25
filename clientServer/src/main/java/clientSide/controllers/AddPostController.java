package clientSide.controllers;

import clientSide.dao.PostDao;
import clientSide.entities.Post;
import clientSide.entities.User;
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

    private final PostDao postDao;

    public AddPostController(PostDao postDao) {
        this.postDao = postDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String addPostString(){
        return "addPost";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPost(Post post, HttpServletRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String[] date = req.getParameter("endDate").split("-");

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        post.setPostDate(new Date());
        post.setExpirationDate(new GregorianCalendar(year,month,day).getTime());

        postDao.addPost(post,authentication);

        return "redirect:/";
    }
}
