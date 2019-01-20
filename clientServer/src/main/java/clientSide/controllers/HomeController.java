package clientSide.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import clientSide.entities.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/home")
public class HomeController{

    @RequestMapping(method = RequestMethod.GET)
    public String homeString(ModelMap modelMap,HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        if(user==null) {
            user = new User();
            user.setUsername("Anonymous");
            return "redirect:/guest";
        }else{
            user.setUsername(user.getUsername());
        }
        modelMap.addAttribute("parameter", user.getUsername());
        return "home";
    }
}
