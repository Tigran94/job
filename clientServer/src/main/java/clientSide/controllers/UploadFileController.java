package clientSide.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.multi.MultiPanelUI;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/uploadFile")
public class UploadFileController {

    private final static String redirect = "redirect:/settings";
    private final static String incorrectFileMessage = "Only PDF files are supported";
    private final static String uploadExceptionMessage = "Something went wrong. Please try again";
    private final static String successfullMessage = "CV uploaded correctly";
    private final static String docxApplication = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private final static String pdfApplication = "application/pdf";

    @PostMapping
    public String uploadServerFile(@RequestParam("cv") MultipartFile file, RedirectAttributes red){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!isFilePdf(file,red)){
            return redirect;
        }

        saveFile(file,authentication.getName(),red);
        return redirect;
    }



    public static boolean isFilePdf(MultipartFile file,RedirectAttributes red){
        if(!isFileSupported(file)){
            red.addFlashAttribute("uploadError",incorrectFileMessage);
            return false;
        }
        return true;
    }


    public void saveFile(MultipartFile file,String username,RedirectAttributes red){
        try{
            saveFile(file,username);
        }catch (Exception e){
            red.addFlashAttribute("uploadError",uploadExceptionMessage);
        }
        red.addFlashAttribute("uploadError",successfullMessage);
    }


    public void saveFile(MultipartFile file,String userName) throws Exception{
        String systemPath = "usersCV" + File.separator + userName;
        File cv = new File(systemPath);
        Files.createDirectories(Paths.get(systemPath));
        systemPath = systemPath + File.separator + getFileType(file.getContentType());
        Files.deleteIfExists(Paths.get(systemPath));
        byte[] bytes = file.getBytes();
        cv = new File(systemPath);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(cv));
        stream.write(bytes);
        stream.close();


    }

    private static String getFileType(String contentType){
        return contentType.equals(pdfApplication) ? "cv.pdf" : "cv.docx";
    }

    private static boolean isFileSupported(MultipartFile file){
        String fileType = file.getContentType();
        return fileType.equals(pdfApplication) || fileType.equals(docxApplication);
    }
}
