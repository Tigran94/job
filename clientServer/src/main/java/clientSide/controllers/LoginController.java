package clientSide.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }

        return "login";
    }

    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String loginError(RedirectAttributes red){
        red.addFlashAttribute("loginErrorMessage","Wrong username or password");
        return "redirect:/login";
    }
}
