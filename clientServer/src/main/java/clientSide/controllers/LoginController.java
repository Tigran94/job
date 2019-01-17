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
@RequestMapping("/")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String homePageString() {
        return "index";
    }

    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public String signUpString(){
        return "signup";
    }
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public ModelAndView guestString(){
        User user = new User();
        user.setUserName("Anonymous");
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("parameter", user.getUserName());
        return modelAndView;
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginString(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletResponse resp,
                              HttpServletRequest req, ModelMap modelMap){

        User user = Security.login(username, password);
        if (user == null) {
            resp.setStatus(401);
            return null;
        }
        req.getSession().setAttribute("user",user);
        modelMap.addAttribute("parameter",user.getUserName());
        return "home";
    }
}
