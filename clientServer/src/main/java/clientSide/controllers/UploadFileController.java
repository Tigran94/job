package clientSide.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping("/uploadFile")
public class UploadFileController {

    private final static String redirect = "redirect:/settings";
    private final static String incorrectFileMessage = "Only PDF files are supported";
    private final static String uploadExceptionMessage = "Something went wrong. Please try again";
    private final static String sucsessfullMessage = "CV uploaded correctly";

    @RequestMapping(method = RequestMethod.POST)
    public String uploadServerFile(@RequestParam("cv") MultipartFile file, RedirectAttributes red){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!isFilePdf(file,red)){
            return redirect;
        }

        saveFile(file,authentication.getName(),red);
        return redirect;
    }


    public static boolean isFilePdf(MultipartFile file,RedirectAttributes red){
        if(!isFilePdf(file)){
            red.addFlashAttribute("uploadError",incorrectFileMessage);
            return false;
        }
        return true;
    }

    public static void saveFile(MultipartFile file,String username,RedirectAttributes red){
        try {
            saveFile(file, username);
        }catch (Exception e){
            red.addFlashAttribute("uploadError",uploadExceptionMessage);
            return;
        }

        red.addFlashAttribute("uploadError",sucsessfullMessage);
        return;
    }

    public static void saveFile(MultipartFile file,String userName) throws Exception{
        byte[] bytes = file.getBytes();

        File dir = new File(new ClassPathResource(File.separator+"usersCV"+File.separator + userName).getPath());

        if (!dir.exists()) {
            dir.mkdirs();
        }
        File serverFile = new File(dir.getAbsolutePath() + File.separator + "cv.pdf");
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

        stream.write(bytes);
        stream.close();
    }

    private static boolean isFilePdf(MultipartFile file){
        return file.getContentType().equals("application/pdf");
    }
}
