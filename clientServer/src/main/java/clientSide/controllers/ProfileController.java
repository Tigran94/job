package clientSide.controllers;

import clientSide.Security;
import clientSide.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping(method = RequestMethod.GET)
    public String profileString(){
        return "profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            HttpServletRequest req){

        ModelAndView modelAndView = new ModelAndView("profile");
        User user= (User)req.getSession().getAttribute("user");


        if(!user.getPassword().equals(currentPassword)){
            modelAndView.addObject("passwordChanged","Wrong password");
            return modelAndView;
        }else{
            Security.changePassword(user,newPassword);
            modelAndView.addObject("passwordChanged","Password is changed successfully");
            return modelAndView;
        }
    }
}