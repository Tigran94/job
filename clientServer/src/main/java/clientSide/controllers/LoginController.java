package clientSide.controllers;

import clientSide.dao.UserDao;
import clientSide.entities.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserDao userDao;

    public LoginController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }

        return "login";
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public String loginString(@RequestParam("username") String username,
//                              @RequestParam("password") String password,
//                              HttpServletResponse resp,
//                              HttpServletRequest req, RedirectAttributes red){
//
//        User user = userDao.login(username, password);
//        if (user == null) {
//            red.addFlashAttribute("loginConfirmedLogin","*User not found");
//            return "redirect:/login";
//        }else {
//            req.getSession().setAttribute("user",user);
//            return "redirect:/home";
//        }
//
//    }
}
