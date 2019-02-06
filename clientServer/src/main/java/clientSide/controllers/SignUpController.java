package clientSide.controllers;

import clientSide.entities.Company;
import clientSide.services.CompanyDao;
import clientSide.services.UserDao;
import clientSide.entities.User;

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


    private final UserDao userDao;
    private final CompanyDao companyDao;

    public SignUpController(UserDao userDao, CompanyDao companyDao) {
        this.userDao = userDao;
        this.companyDao = companyDao;
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
                                   HttpServletRequest req, RedirectAttributes red, User user){


        if(!(user.getPassword().equals(confirmPass))){
            red.addFlashAttribute("passwordConfirmed","Wrong confirm password");
           return "redirect:/signup/userSignup";
        }

        if(!UploadFileController.isFilePdf(file,red)){
            return "redirect:/signup/userSignup";
        }

        String messages = userDao.checkIfUserExists(user.getUsername(),user.getEmail());

        if(messages.length()!=0){
            red.addFlashAttribute("registrationFailed",messages);
            return "redirect:/signup/userSignup";
        }

        userDao.registerUser(user);
        req.getSession().setAttribute("user",user);

        try {
            UploadFileController.saveFile(file, user.getUsername());
        }catch (Exception ignore){}

        return "redirect:/";

    }
    @RequestMapping(value = "/companySignup",method = RequestMethod.POST)
    public String companyRegistration(@RequestParam("confirmPassword") String confirmPass,
                                   HttpServletRequest req, RedirectAttributes red, Company company){


        if(!(company.getPassword().equals(confirmPass))){
            red.addFlashAttribute("passwordConfirmed","Wrong confirm password");
            return "redirect:/signup/companySignup";
        }

        String messages = userDao.checkIfUserExists(company.getCompanyName(),company.getEmail());

        if(messages.length()!=0){
            red.addFlashAttribute("registrationFailed",messages);
            return "redirect:/signup/companySignup";
        }

        companyDao.registerCompany(company);
        req.getSession().setAttribute("company",company);

        return "redirect:/";

    }

}
