package clientSide.controllers;

import clientSide.dao.UserDao;
import clientSide.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("/signup")
public class SignUpController {


    private final UserDao userDao;

    public SignUpController(UserDao userDao) {
        this.userDao = userDao;
    }

    public ArrayList<String> messages = new ArrayList<String>();
    public String passwordConfirmMessage = "";

    @RequestMapping(method = RequestMethod.GET)
    public String signupString(ModelMap modelMap){
        String tempMessages = messages.toString().substring(1,messages.toString().length()-1);
        String tempPasswordConfirmedMessage = new String(passwordConfirmMessage);

        modelMap.addAttribute("registrationFailed",tempMessages);
        modelMap.addAttribute("passwordConfirmed",tempPasswordConfirmedMessage);

        messages.clear();
        passwordConfirmMessage = "";
        return "signup";
    }

    @RequestMapping(value="/registerUser",method = RequestMethod.POST)
    public String userRegistration(@RequestParam("confirmPassword") String confirmPass,
                               HttpServletRequest req,User user){

        if(!(user.getPassword().equals(confirmPass))){
            passwordConfirmMessage = "Wrong confirm password";
           return "redirect:/signup";
        }
        messages = userDao.checkIfUserExists(user.getUsername(),user.getEmail());
        if(messages.size()!=0){
            return "redirect:/signup";
        }else{
            userDao.registerUser(user);
            req.getSession().setAttribute("user",user);
            return "redirect:/home";
        }


    }

}
