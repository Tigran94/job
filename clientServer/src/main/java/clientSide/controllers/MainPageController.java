package clientSide.controllers;

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
@RequestMapping("/")
public class MainPageController {

    private final UserDao userDao;

    public MainPageController(UserDao userDao) {
        this.userDao = userDao;
    }
    String message = "";

    @RequestMapping(method = RequestMethod.GET)
    public String homePageString(ModelMap modelMap,HttpServletRequest req) {
        String temp = new String(message);
        modelMap.addAttribute("loginConfirmedMain",temp);

        if(req.getSession().getAttribute("user")!=null){
            modelMap.addAttribute("loginConfirmedMain",null);
            return "redirect:/home";
        }
        message="";

        return "index";
    }
    @RequestMapping(value = "/guest",method = RequestMethod.GET)
    public String guestString(ModelMap modelMap){
        User user = new User();
        user.setUsername("Anonymous");
        modelMap.addAttribute("parameter", user.getUsername());
        modelMap.addAttribute("hiddenButton","hidden");
        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String loginString(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletResponse resp,
                              HttpServletRequest req, ModelMap modelMap){

        User user = userDao.login(username, password);
        if (user == null) {
            message="User not found";
            return "redirect:/";
        }else {
            req.getSession().setAttribute("user",user);
            return "redirect:/home";
        }

    }

}
