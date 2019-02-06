package clientSide.services;

import clientSide.dto.JobTitle;
import clientSide.entities.Company;
import clientSide.entities.Post;
import clientSide.entities.User;
import clientSide.repositories.CompanyRepository;
import clientSide.repositories.PostRepository;
import clientSide.repositories.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostDao {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CompanyRepository companyRepository;

    public PostDao(UserRepository userRepository, PostRepository postRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.companyRepository = companyRepository;
    }

    public Post addPost(Post post, Authentication authUser) {
        Company company = companyRepository.findByUsername(authUser.getName()).orElse(null);

        post.setUser(company);
        post.setEmail(company.getEmail());

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
    public List<Post> getJobTitlesForFilter() {

        List<Post> posts = (List<Post>)postRepository.findAll();
        return posts;
    }
    public List<JobTitle> getJobTitlesWithoutCompany(String username) {
        Company company = companyRepository.findByUsername(username).orElse(null);

        List<Post> posts = (List<Post>)postRepository.findAll();
        return posts
                .stream()
                .filter(u -> !u.getEmail().equals(company.getEmail()))
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
    public List<Post> getJobTitlesWithoutCompanyForFilter() {

        List<Post> posts = (List<Post>)postRepository.findAll();
        return posts;
    }

    public List<Post> getJobTitlesForComapnyForFilter(String username) {
        Company company = companyRepository.findByUsername(username).orElse(null);

        List<Post> posts = postRepository.findByEmail(company.getEmail());

        return posts;
    }
    public List<JobTitle> getJobTitlesForComapny(String username) {
        Company company = companyRepository.findByUsername(username).orElse(null);

        List<Post> posts = postRepository.findByEmail(company.getEmail());

        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }






    public Post getJobAnnouncementByIdWithStream(long id) {
        return postRepository.findById(id).orElse(null);
    }




























    public List<JobTitle> getJobTitles(String type, String salary, String workTime) {
        List<Post> posts = getFiltered(type,salary,workTime);
        return posts
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }













    public List<JobTitle> getJobTitlesForCompany(String type, String salary, String workTime,Authentication authUser) {

        List<Post> posts;
        Company company = companyRepository.findByUsername(authUser.getName()).orElse(null);

        posts= getFiltered(type,salary,workTime,authUser,true);
        return posts
                .stream()
                .filter(u -> u.getEmail().equals(company.getEmail()))
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }




    public List<JobTitle> getJobTitlesWithoutCompany(String type, String salary, String workTime,Authentication authUser) {

        List<Post> posts;
        Company company = companyRepository.findByUsername(authUser.getName()).orElse(null);

        posts= getFiltered(type,salary,workTime,authUser,false);
        return posts
                .stream()
                .filter(u -> !u.getEmail().equals(company.getEmail()))
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());

    }



    private List<Post> getFiltered(String type, String salary, String workTime) {

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
            return getJobTitlesForFilter();
        }

        return posts;
    }


    private List<Post> getFiltered(String type, String salary, String workTime,Authentication authUser, boolean state){
        Company company = companyRepository.findByUsername(authUser.getName()).orElse(null);

        List<Post> posts=null;

        if (!type.equals("")) {
            posts = postRepository.findByTypeAndEmail(type,company.getEmail());
        }
        if (!workTime.equals("")){
            if(posts!=null){
                posts
                        .stream()
                        .filter(t->t.getWorkTime().equals(workTime))
                        .collect(Collectors.toList());
            }
            else
                posts = postRepository.findByWorkTimeAndEmail(workTime,company.getEmail());
        }
        if (!salary.equals("")){
            if(posts!=null){
                posts
                        .stream()
                        .filter(t->t.getSalary().equals(salary))
                        .collect(Collectors.toList());
            }
            else
                posts = postRepository.findBySalaryAndEmail(salary,company.getEmail());
        }
        if(type.equals("") && workTime.equals("") && salary.equals("")){
            if(!state){
                return getJobTitlesWithoutCompanyForFilter();
            }
            return getJobTitlesForComapnyForFilter(company.getUsername());
        }
        return posts;
    }
}
