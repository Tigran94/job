package clientSide.controllers;

import clientSide.dao.PostDao;
import clientSide.entities.Post;
import clientSide.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
        User user=null;
        if(req.getSession().getAttribute("user") instanceof User){
            user = (User) req.getSession().getAttribute("user");
        }

        postDao.addPost(post,user);

        return "redirect:/home";
    }
}
