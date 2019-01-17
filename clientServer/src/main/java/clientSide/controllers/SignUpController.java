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
@RequestMapping("/signup")
public class SignUpController {


    @RequestMapping(method = RequestMethod.GET)
    public String signupString(){
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(@RequestParam("username") String username, @RequestParam("password") String password,
                               @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email,
                               @RequestParam("confirmPassword") String confirmPass,
                               HttpServletRequest req, HttpServletResponse resp, ModelMap modelMap){

        if(!(password.equals(confirmPass))){
           modelMap.addAttribute("passwordConfirmed","Wrong confirmation password");
           return "signup";
        }
        User user = Security.registerUser(username,firstName,lastName,email, password);
        req.getSession().setAttribute("user",user);
        return "home";

    }

}
