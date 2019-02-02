package clientSide.controllers;

import clientSide.services.UserDao;
import clientSide.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    private final UserDao userDao;

    public SettingsController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String settingsString(HttpServletRequest req) {
        return "settings";
    }

    @RequestMapping(value = "/pass",method = RequestMethod.POST)
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            HttpServletRequest req, RedirectAttributes red){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user= userDao.getUser(authentication.getName());

        if(!user.getPassword().equals(currentPassword)){
            red.addFlashAttribute("passwordChanged","Wrong password");
            return "redirect:/settings";
        }
        else{
            userDao.changePassword(authentication,newPassword);
            red.addFlashAttribute("passwordChanged","Password changed successfully");
            return "redirect:/settings";
        }
    }

    @RequestMapping(value = "/fn",method = RequestMethod.POST)
    public String changeFirstName(
            @RequestParam("firstName") String firstName,
            HttpServletRequest req, RedirectAttributes red){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user= userDao.getUser(authentication.getName());


        if(user.getFirstName().equals(firstName)){
            red.addFlashAttribute("firstNameChanged","Your name is already "+firstName);
            return "redirect:/settings";
        }
        else{
            userDao.changeFirstName(authentication,firstName);
            red.addFlashAttribute("firstNameChanged","First Name changed successfully");
            return "redirect:/settings";
        }
    }

    @RequestMapping(value = "/ln",method = RequestMethod.POST)
    public String changeLastName(
            @RequestParam("lastName") String lastName,
            HttpServletRequest req, RedirectAttributes red){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user= userDao.getUser(authentication.getName());


        if(user.getLastName().equals(lastName)){
            red.addFlashAttribute("lastNameChanged","Your last name is already "+lastName);
            return "redirect:/settings";
        }
        else{
            userDao.changeLastName(authentication,lastName);
            red.addFlashAttribute("lastNameChanged","Last Name changed successfully");
            return "redirect:/settings";
        }
    }
}
