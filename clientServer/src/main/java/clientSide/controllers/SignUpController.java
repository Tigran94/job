package clientSide.controllers;

import clientSide.entities.CompanyEntity;
import clientSide.services.CompanyService;
import clientSide.services.UserService;
import clientSide.entities.UserEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/signup")
public class SignUpController {


    private final UserService userService;
    private final CompanyService companyService;

    public SignUpController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String signupString(){
        return "signup";
    }

    @RequestMapping(value = "/userSignup",method = RequestMethod.GET)
    public String userSignupString(){
        return "userSignup";
    }
    @RequestMapping(value = "/companySignup",method = RequestMethod.GET)
    public String companySignupString(){
        return "companySignup";
    }


    @RequestMapping(value = "/userSignup",method = RequestMethod.POST)
    public String userRegistration(@RequestParam("confirmPassword") String confirmPass,
                                   @RequestParam("pdfFile") MultipartFile file,
                                   HttpServletRequest req, RedirectAttributes red, UserEntity userEntity){

        if(!(userEntity.getPassword().equals(confirmPass))){
            red.addFlashAttribute("passwordConfirmed","Wrong confirm password");
           return "redirect:/signup/userSignup";
        }

        if(!UploadFileController.isFilePdf(file,red)){
            return "redirect:/signup/userSignup";
        }

        String messages = userService.checkIfUserExists(userEntity.getUsername(), userEntity.getEmail());

        if(messages.length()!=0){
            red.addFlashAttribute("registrationFailed",messages);
            return "redirect:/signup/userSignup";
        }

        userService.registerUser(userEntity);

        try {
            UploadFileController.saveFile(file, userEntity.getUsername());
        }catch (Exception ignore){}

        return "redirect:/";

    }
    @RequestMapping(value = "/companySignup",method = RequestMethod.POST)
    public String companyRegistration(@RequestParam("confirmPassword") String confirmPass,
                                      RedirectAttributes red, CompanyEntity companyEntity){


        if(!(companyEntity.getPassword().equals(confirmPass))){
            red.addFlashAttribute("passwordConfirmed","Wrong confirm password");
            return "redirect:/signup/companySignup";
        }

        String messages = userService.checkIfUserExists(companyEntity.getCompanyName(), companyEntity.getEmail());

        if(messages.length()!=0){
            red.addFlashAttribute("registrationFailed",messages);
            return "redirect:/signup/companySignup";
        }

        companyService.registerCompany(companyEntity);

        return "redirect:/";

    }

}
