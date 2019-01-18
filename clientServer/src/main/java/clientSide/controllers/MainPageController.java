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
public class MainPageController {

    @RequestMapping(method = RequestMethod.GET)
    public String homePageString() {
        return "index";
    }

    @RequestMapping(value = "/guest",method = RequestMethod.GET)
    public String guestString(ModelMap modelMap){
        User user = new User();
        user.setUserName("Anonymous");
        modelMap.addAttribute("parameter", user.getUserName());
        modelMap.addAttribute("hiddenButton","hidden");
        return "home";
    }


}
