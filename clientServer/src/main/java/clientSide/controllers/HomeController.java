package clientSide.controllers;

import clientSide.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController{

    @RequestMapping(method = RequestMethod.GET)
    public String homeString(ModelMap modelMap,HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        if(user==null) {
            user = new User();
            user.setUserName("Anonymous");
        }else{
            user.setUserName(user.getUserName());
        }
        modelMap.addAttribute("parameter", user.getUserName());
        return "home";
    }
}
