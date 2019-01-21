package clientSide.controllers;

import clientSide.dao.UserDao;
import clientSide.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("/signup")
public class SignUpController {


    private final UserDao userDao;

    public SignUpController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String signupString(ModelMap modelMap){
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String userRegistration(@RequestParam("confirmPassword") String confirmPass,
                                   HttpServletRequest req, RedirectAttributes red, User user){

        if(!(user.getPassword().equals(confirmPass))){
            red.addFlashAttribute("passwordConfirmed","Wrong confirm password");
           return "redirect:/signup";
        }

        String messages = userDao.checkIfUserExists(user.getUsername(),user.getEmail());

        if(messages.length()!=0){
            red.addFlashAttribute("registrationFailed",messages);
            return "redirect:/signup";
        }

        userDao.registerUser(user);
        req.getSession().setAttribute("user",user);
        return "redirect:/home";



    }

}