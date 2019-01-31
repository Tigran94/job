package clientSide.services;

import clientSide.dto.JobTitle;
import clientSide.entities.Post;
import clientSide.entities.User;
import clientSide.repositories.PostRepository;
import clientSide.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostDao {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostDao(UserRepository userRepository,PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Post addPost(Post post, Authentication authUser) {
        User user = userRepository.findByUsername(authUser.getName()).orElse(null);

        post.setUser(user);
        post.setEmail(user.getEmail());

        postRepository.save(post);

        return post;
    }

    public void deletePost(long id){
        Post post =  postRepository.findById(id).orElse(null);
        postRepository.delete(post);
    }

    public List<JobTitle> getJobTitles() {

        List<Post> posts = (List<Post>)postRepository.findAll();
        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }

    public List<JobTitle> getJobTitles(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        List<Post> posts = postRepository.findByEmail(user.getEmail());

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }

    public List<JobTitle> getJobTitles(String type, String salary, String workTime,Authentication... authUser) {
        if(authUser.length!=0){
            return getJobTitlesStream(type,salary,workTime,authUser[0]);
        }
        else
            return getJobTitlesStream(type,salary,workTime);
    }
    public Post getJobAnnouncementByIdWithStream(long id) {
        return postRepository.findById(id).orElse(null);
    }

    private List<JobTitle> getJobTitlesStream(String type, String salary, String workTime) {

        List<Post> posts;

        if(!type.equals("") && !salary.equals("") && !workTime.equals("")){
             posts = postRepository.findByTypeAndSalaryAndWorkTime(type,salary,workTime);
        }else if(!type.equals("") && !salary.equals("")){
            posts = postRepository.findByTypeAndSalary(type,salary);
        }
        else if(!type.equals("") && !workTime.equals("")){
            posts = postRepository.findByTypeAndWorkTime(type,workTime);
        }else if(!salary.equals("") && !workTime.equals("")){
           posts = postRepository.findBySalaryAndWorkTime(salary,workTime);
        }else if(!salary.equals("")){
            posts = postRepository.findBySalary(salary);

        }else if(!workTime.equals("")){
            posts = postRepository.findByWorkTime(workTime);

        }else if(!type.equals("")){
            posts = postRepository.findByType(type);

        }else{
            return getJobTitles();
        }
        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
    private List<JobTitle> getJobTitlesStream(String type, String salary, String workTime,Authentication authUser) {
        User user = userRepository.findByUsername(authUser.getName()).orElse(null);


        String sql;
        List<Post> posts;

        if(!type.equals("") && !salary.equals("") && !workTime.equals("")){
          posts = postRepository.findByTypeAndSalaryAndWorkTimeAndEmail(type,salary,workTime,user.getEmail());
        }else if(!type.equals("") && !salary.equals("")){
            posts = postRepository.findByTypeAndSalaryAndEmail(type,salary,user.getEmail());
        }
        else if(!type.equals("") && !workTime.equals("")){
            posts = postRepository.findByTypeAndWorkTimeAndEmail(type,workTime,user.getEmail());
        }else if(!salary.equals("") && !workTime.equals("")){
            posts = postRepository.findBySalaryAndWorkTimeAndEmail(salary,workTime,user.getEmail());

        }else if(!salary.equals("")){
            posts = postRepository.findBySalaryAndEmail(salary,user.getEmail());

        }else if(!workTime.equals("")){
            posts = postRepository.findByWorkTimeAndEmail(workTime,user.getEmail());

        }else if(!type.equals("")){
            posts = postRepository.findByTypeAndEmail(type,user.getEmail());

        }else{
            return getJobTitles(user.getUsername());
        }

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
}
