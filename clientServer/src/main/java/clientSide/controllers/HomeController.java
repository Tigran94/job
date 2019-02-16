package clientSide.controllers;

import clientSide.dto.JobTitle;
import clientSide.dto.PostDto;
import clientSide.dto.PostSearchDto;
import clientSide.entities.PostEntity;
import clientSide.search.PostSearch;
import clientSide.services.PostService;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController{
    private final PostService postService;
    private final JavaMailSender mailSender;
    private final String storage = "usersCV";


    public HomeController(PostService postService, JavaMailSender mailSender) {
        this.postService = postService;
        this.mailSender = mailSender;
    }

    @GetMapping
    public ModelAndView homeString(){
        List<JobTitle> jobTitles = postService.getJobTitles();

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("jobTitles",jobTitles);
        return modelAndView;
    }


    @PostMapping
    public ModelAndView homeFiltered(PostSearchDto postSearchDto){
        PostSearch postSearch = new PostSearch(postSearchDto.getType(),postSearchDto.getSalary(),postSearchDto.getWorkTime());
        List<JobTitle> jobTitles = postService.getJobTitles(postSearch);
        ModelAndView modelAndView = new ModelAndView("home");
        if(jobTitles.isEmpty()){
            modelAndView.addObject("noJobTitlesFound","No job titles found with that search criteria");
            return modelAndView;
        }
        modelAndView.addObject("jobTitles",jobTitles);
        return modelAndView;
    }

    @PostMapping(value = "/apply")
    public String applyPost(@RequestParam("jobId") long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PostEntity postEntity = postService.getJobAnnouncementByIdWithStream(id);
        String path = storage + File.separator + authentication.getName();
        path = new File(path).getAbsolutePath();
        File userFolder = new File(path);
        File userCv = new File(path + File.separator + userFolder.list()[0]);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject("Job Application");
            helper.setText("Hi, this cv is coming from job.am, please pay attention to him\n"
                    + "My name is " +authentication.getName() + " and i really need this job");
            helper.setTo(postEntity.getEmail());
            helper.setFrom("springtest94@gmail.com");
            helper.addAttachment(userCv.getName(), userCv);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
        return "redirect:/";
    }

    @GetMapping(value = "/{jobId}")
    public ResponseEntity getJobById(@PathVariable("jobId") long id) {
        PostDto postDto = new PostDto(postService.getJobAnnouncementByIdWithStream(id));
        return ResponseEntity.ok(postDto);
    }
}
