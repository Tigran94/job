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

        List<Post> posts=null;

        if (!type.equals("")) {
            posts = postRepository.findByType(type);
        }
        if (!workTime.equals("")){
            if(posts!=null){
                 posts
                        .stream()
                        .filter(t->t.getWorkTime().equals(workTime))
                        .collect(Collectors.toList());
            }
            else
            posts = postRepository.findByWorkTime(workTime);
        }
        if (!salary.equals("")){
            if(posts!=null){
                posts
                        .stream()
                        .filter(t->t.getSalary().equals(salary))
                        .collect(Collectors.toList());
            }
            else
            posts = postRepository.findBySalary(salary);
        }
        if(type.equals("") && workTime.equals("") && salary.equals("")){
            return getJobTitles();
        }

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
    private List<JobTitle> getJobTitlesStream(String type, String salary, String workTime,Authentication authUser) {
        User user = userRepository.findByUsername(authUser.getName()).orElse(null);

        List<Post> posts=null;

        if (!type.equals("")) {
            posts = postRepository.findByTypeAndEmail(type,user.getEmail());
        }
        if (!workTime.equals("")){
            if(posts!=null){
                posts
                        .stream()
                        .filter(t->t.getWorkTime().equals(workTime))
                        .collect(Collectors.toList());
            }
            else
                posts = postRepository.findByWorkTimeAndEmail(workTime,user.getEmail());
        }
        if (!salary.equals("")){
            if(posts!=null){
                posts
                        .stream()
                        .filter(t->t.getSalary().equals(salary))
                        .collect(Collectors.toList());
            }
            else
                posts = postRepository.findBySalaryAndEmail(salary,user.getEmail());
        }
        if(type.equals("") && workTime.equals("") && salary.equals("")){
            return getJobTitles(user.getUsername());
        }
        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
}
