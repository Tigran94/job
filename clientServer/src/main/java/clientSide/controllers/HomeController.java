package clientSide.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import clientSide.entities.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/home")
public class HomeController{

    @RequestMapping(method = RequestMethod.GET)
    public String homeString(ModelMap modelMap,HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        if(user==null) {
            user = new User();
            user.setUserName("Anonymous");
            return "redirect:/guest";
        }else{
            user.setUserName(user.getUserName());
        }
        modelMap.addAttribute("parameter", user.getUserName());
        return "home";
    }
}
