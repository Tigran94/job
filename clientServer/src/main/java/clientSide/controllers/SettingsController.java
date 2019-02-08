package clientSide.controllers;

import clientSide.services.UserService;
import clientSide.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public SettingsController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView settingsString(HttpServletRequest req) {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("settings");
        if(authentication.getAuthorities().toString().contains("ROLE_ADMIN")){
            modelAndView.addObject("logoutButton","hide");
            modelAndView.addObject("userAdmin","Hide");
            return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping(value = "/pass",method = RequestMethod.POST)
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            HttpServletRequest req, RedirectAttributes red){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userService.getUser(authentication.getName());

        if(!passwordEncoder.matches(currentPassword,userEntity.getPassword())){
            red.addFlashAttribute("passwordChanged","Wrong password");
            return "redirect:/settings";
        }
        else{
            userService.changePassword(authentication,newPassword);
            red.addFlashAttribute("passwordChanged","Password changed successfully");
            return "redirect:/settings";
        }
    }

    @RequestMapping(value = "/fn",method = RequestMethod.POST)
    public String changeFirstName(
            @RequestParam("firstName") String firstName,
            HttpServletRequest req, RedirectAttributes red){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userService.getUser(authentication.getName());


        if(userEntity.getFirstName().equals(firstName)){
            red.addFlashAttribute("firstNameChanged","Your name is already "+firstName);
            return "redirect:/settings";
        }
        else{
            userService.changeFirstName(authentication,firstName);
            red.addFlashAttribute("firstNameChanged","First Name changed successfully");
            return "redirect:/settings";
        }
    }

    @RequestMapping(value = "/ln",method = RequestMethod.POST)
    public String changeLastName(
            @RequestParam("lastName") String lastName,
            HttpServletRequest req, RedirectAttributes red){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userService.getUser(authentication.getName());


        if(userEntity.getLastName().equals(lastName)){
            red.addFlashAttribute("lastNameChanged","Your last name is already "+lastName);
            return "redirect:/settings";
        }
        else{
            userService.changeLastName(authentication,lastName);
            red.addFlashAttribute("lastNameChanged","Last Name changed successfully");
            return "redirect:/settings";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
