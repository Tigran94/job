package clientSide.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping(value = "/error")
    public String loginError(RedirectAttributes red){
        red.addFlashAttribute("loginErrorMessage","Wrong username or password");
        return "redirect:/login";
    }
}
