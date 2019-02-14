package clientSide.controllers;

import clientSide.entities.CompanyEntity;
import clientSide.services.CompanyService;
import clientSide.services.UserService;
import clientSide.entities.UserEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/signup")
public class SignUpController {


    private final UserService userService;
    private final CompanyService companyService;

    public SignUpController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @GetMapping
    public String signupString(){
        return "signup";
    }

    @GetMapping(value = "/userSignup")
    public String userSignupString(){
        return "userSignup";
    }
    @GetMapping(value = "/companySignup")
    public String companySignupString(){
        return "companySignup";
    }


    @PostMapping(value = "/userSignup")
    public String userRegistration(@RequestParam("confirmPassword") String confirmPass,
                                   @RequestParam("pdfFile") MultipartFile file,
                                   RedirectAttributes red, UserEntity userEntity){

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
        UploadFileController uploadFileController = new UploadFileController();

        try {
            uploadFileController.saveFile(file, userEntity.getUsername());
        }catch (Exception ignore){}

        return "redirect:/";

    }
    @PostMapping(value = "/companySignup")
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
