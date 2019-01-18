package clientSide.controllers;

import clientSide.Security;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/addPost")
public class AddPostController {

    @RequestMapping(method = RequestMethod.GET)
    public String addPostString(){
        return "addPost";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPostString2(@RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("type") String type,
                                 @RequestParam("salary") String salary,
                                 @RequestParam("email") String email,
                                 @RequestParam("workTime") String workTime){


        Security.addPost(title,description,type,salary,email, workTime);

        return "redirect:/home";
    }
}
