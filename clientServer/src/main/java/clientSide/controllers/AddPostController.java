package clientSide.controllers;

import clientSide.dao.PostDao;
import clientSide.entities.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String addPost(Post post){

        postDao.addPost(post);

        return "redirect:/home";
    }
}
