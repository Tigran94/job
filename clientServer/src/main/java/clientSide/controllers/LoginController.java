package clientSide.controllers;

import clientSide.dao.Security;
import clientSide.dao.UserDao;
import clientSide.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    String message = "";

    private final UserDao userDao;

    public LoginController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String homePageString(ModelMap modelMap) {
        String temp = new String(message);
        modelMap.addAttribute("loginConfirmedLogin",temp);
        message="";
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String loginString(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletResponse resp,
                              HttpServletRequest req, ModelMap modelMap){

        User user = userDao.login(username, password);
        if (user == null) {
            message="User not found";
            //modelMap.addAttribute("loginConfirmedLogin","User not found");

            return "redirect:/login";
        }else {
            req.getSession().setAttribute("user",user);
            return "redirect:/home";
        }

    }
}
