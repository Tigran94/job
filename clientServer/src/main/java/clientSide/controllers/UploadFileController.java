package clientSide.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping("/uploadFile")
public class UploadFileController implements HandlerExceptionResolver {

    private final static String redirect = "redirect:/settings";
    private final static String incorrectFileMessage = "Only PDF files are supported";
    private final static String uploadExceptionMessage = "Something went wrong. Please try again";
    private final static String sucsessfullMessage = "CV uploaded correctly";
    private final static String docxApplication = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private final static String pdfApplication = "application/pdf";

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
        if(!isFileSupported(file)){
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
        String fileType = file.getContentType();
        File serverFile = null;

        if(fileType.equals(pdfApplication)) {
            serverFile = new File(dir.getAbsolutePath() + File.separator + "cv.pdf");
        }
        if(fileType.equals(docxApplication)){
            serverFile = new File(dir.getAbsolutePath() + File.separator + "cv.docx");
        }

        if(serverFile.exists()){
            serverFile.delete();
        }

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

        stream.write(bytes);
        stream.close();
    }

    private static boolean isFileSupported(MultipartFile file){
        String fileType = file.getContentType();
        return fileType.equals(pdfApplication) || fileType.equals(docxApplication);
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView modelAndView = new ModelAndView("settings");

        modelAndView.addObject("uploadError",uploadExceptionMessage);

        return modelAndView;
    }
}
