package clientSide.controllers;

import clientSide.dao.UserDao;
import clientSide.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserDao userDao;

    public ProfileController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String profileString(HttpServletRequest req , ModelMap modelMap){

        if(req.getSession().getAttribute("user") == null){
            return "redirect:/home";
        }
        return "profile";
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String logOutString(HttpServletRequest req){
        req.getSession().setAttribute("user",null);
        return "redirect:/";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            HttpServletRequest req, RedirectAttributes red){

        User user= (User)req.getSession().getAttribute("user");


        if(!user.getPassword().equals(currentPassword)){
            red.addFlashAttribute("passwordChanged","Wrong password");
            return "redirect:/profile";
        }
        else{
            userDao.changePassword(user,newPassword);
            red.addFlashAttribute("passwordChanged","Password changed sucessfully");
            return "redirect:/profile";
        }
    }
}