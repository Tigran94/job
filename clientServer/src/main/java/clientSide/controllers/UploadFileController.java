package clientSide.controllers;


import clientSide.entities.User;
import org.apache.commons.fileupload.FileUpload;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping("/uploadFile")
public class UploadFileController {

    private static final Logger logger = LoggerFactory.logger(FileUpload.class);

    @RequestMapping(method = RequestMethod.POST)
    public String uploadServerFile(@RequestParam("image") MultipartFile file,HttpServletRequest req, HttpServletResponse resp){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!file.isEmpty()){
            try{
                byte[] bytes = file.getBytes();
                String rootPath = System.getProperty("catalina.home");
                String userName = authentication.getName();
                File dir = new File(rootPath + File.separator + "pictures" + File.separator + userName);

                if(!dir.exists()){
                    dir.mkdirs();
                }
                File serverFile = new File(dir.getAbsolutePath() + File.separator + "image.jpg");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                System.out.println("File loaded succesfully");


            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "redirect:/settings";
    }
}
