package clientSide.controllers;

import clientSide.entities.CompanyEntity;
import clientSide.services.CompanyService;
import clientSide.services.UserService;
import clientSide.entities.UserEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/settings")
public class SettingsController {


    private final UserService userService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    public SettingsController(UserService userService, CompanyService companyService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ModelAndView settingsString() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("settings");
        if(authentication.getAuthorities().toString().contains("ROLE_ADMIN")){
            modelAndView.addObject("logoutButton","hide");
            modelAndView.addObject("userAdmin","Hide");
            return modelAndView;
        }
        return modelAndView;
    }

    @PostMapping(value = "/pass")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword, RedirectAttributes red){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getAuthorities().toString().contains("ROLE_USER")){
            UserEntity userEntity = userService.getUser(authentication.getName());

           return changePassword(currentPassword,newPassword,red,authentication,userEntity);
        }
        CompanyEntity companyEntity = companyService.getUser(authentication.getName());

        return changePassword(currentPassword,newPassword,red,authentication,companyEntity);

    }
    @PostMapping(value = "/fn")
    public String changeFirstName(@RequestParam("firstName") String firstName, RedirectAttributes red){

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

    @PostMapping(value = "/ln")
    public String changeLastName(@RequestParam("lastName") String lastName,RedirectAttributes red){

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

    @GetMapping(value = "/")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    private String changePassword(String currentPassword, String newPassword, RedirectAttributes red, Authentication authentication, Object entity){

        if(entity instanceof UserEntity){
            if(!passwordEncoder.matches(currentPassword,((UserEntity)entity).getPassword())){
                red.addFlashAttribute("passwordChanged","Wrong password");
                return "redirect:/settings";
            }
            else{
                userService.changePassword(authentication,newPassword);
                red.addFlashAttribute("passwordChanged","Password changed successfully");
                return "redirect:/settings";
            }
        }
        if(!passwordEncoder.matches(currentPassword,((CompanyEntity)entity).getPassword())){
            red.addFlashAttribute("passwordChanged","Wrong password");
            return "redirect:/settings";
        }
        else{
            companyService.changePassword(authentication,newPassword);
            red.addFlashAttribute("passwordChanged","Password changed successfully");
            return "redirect:/settings";
        }
    }
}
