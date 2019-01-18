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
import java.util.ArrayList;

@Controller
@RequestMapping("/signup")
public class SignUpController {


    public ArrayList<String> messages = new ArrayList<String>();
    public String passwordConfirmMessage = "";

    @RequestMapping(method = RequestMethod.GET)
    public String signupString(ModelMap modelMap){
        ArrayList<String> tempMessages =(ArrayList<String>) messages.clone();
        String tempPasswordConfirmedMessage = new String(passwordConfirmMessage);

        modelMap.addAttribute("registrationFailed",tempMessages);
        modelMap.addAttribute("passwordConfirmed",tempPasswordConfirmedMessage);

        messages.clear();
        passwordConfirmMessage = "";
        return "signup";
    }

    @RequestMapping(value="/registerUser",method = RequestMethod.POST)
    public String userRegistration(@RequestParam("username") String username, @RequestParam("password") String password,
                               @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email,
                               @RequestParam("confirmPassword") String confirmPass,
                               HttpServletRequest req){

        if(!(password.equals(confirmPass))){
            passwordConfirmMessage = "Wrong confirm password";
           return "redirect:/signup";
        }
        messages = Security.checkIfUserExists(username,email);
        if(messages.size()!=0){
            return "redirect:/signup";
        }else{
            User user = Security.registerUser(username,firstName,lastName,email, password);
            req.getSession().setAttribute("user",user);
            return "redirect:/home";
        }


    }

}
