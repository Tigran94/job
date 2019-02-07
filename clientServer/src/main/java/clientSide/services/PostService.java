package clientSide.services;

import clientSide.dto.JobTitle;
import clientSide.entities.CompanyEntity;
import clientSide.entities.PostEntity;
import clientSide.repositories.CompanyRepository;
import clientSide.repositories.PostRepository;
import clientSide.repositories.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CompanyRepository companyRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.companyRepository = companyRepository;
    }

    public PostEntity addPost(PostEntity postEntity, Authentication authUser) {
        CompanyEntity companyEntity = companyRepository.findByUsername(authUser.getName()).orElse(null);

        postEntity.setUser(companyEntity);
        postEntity.setCompany(companyEntity.getCompanyName());
        postEntity.setEmail(companyEntity.getEmail());

        postRepository.save(postEntity);

        return postEntity;
    }

    public void deletePost(long id){
        PostEntity postEntity =  postRepository.findById(id).orElse(null);
        postRepository.delete(postEntity);
    }

    public List<JobTitle> getJobTitles() {

        List<PostEntity> postEntities = (List<PostEntity>)postRepository.findAll();
        return postEntities
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
    public List<PostEntity> getJobTitlesForFilter() {

        List<PostEntity> postEntities = (List<PostEntity>)postRepository.findAll();
        return postEntities;
    }
    public List<JobTitle> getJobTitlesWithoutCompany(String username) {
        CompanyEntity companyEntity = companyRepository.findByUsername(username).orElse(null);

        List<PostEntity> postEntities = (List<PostEntity>)postRepository.findAll();
        return postEntities
                .stream()
                .filter(u -> !u.getEmail().equals(companyEntity.getEmail()))
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }
    public List<PostEntity> getJobTitlesWithoutCompanyForFilter() {

        List<PostEntity> postEntities = (List<PostEntity>)postRepository.findAll();
        return postEntities;
    }

    public List<PostEntity> getJobTitlesForComapnyForFilter(String username) {
        CompanyEntity companyEntity = companyRepository.findByUsername(username).orElse(null);

        List<PostEntity> postEntities = postRepository.findByEmail(companyEntity.getEmail());

        return postEntities;
    }
    public List<JobTitle> getJobTitlesForComapny(String username) {
        CompanyEntity companyEntity = companyRepository.findByUsername(username).orElse(null);

        List<PostEntity> postEntities = postRepository.findByEmail(companyEntity.getEmail());

        return postEntities
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }






    public PostEntity getJobAnnouncementByIdWithStream(long id) {
        return postRepository.findById(id).orElse(null);
    }




























    public List<JobTitle> getJobTitles(String type, String salary, String workTime) {
        List<PostEntity> postEntities = getFiltered(type,salary,workTime);
        return postEntities
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }













    public List<JobTitle> getJobTitlesForCompany(String type, String salary, String workTime,Authentication authUser) {

        List<PostEntity> postEntities;
        CompanyEntity companyEntity = companyRepository.findByUsername(authUser.getName()).orElse(null);

        postEntities = getFiltered(type,salary,workTime,authUser,true);
        return postEntities
                .stream()
                .filter(u -> u.getEmail().equals(companyEntity.getEmail()))
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }




    public List<JobTitle> getJobTitlesWithoutCompany(String type, String salary, String workTime,Authentication authUser) {

        List<PostEntity> postEntities;
        CompanyEntity companyEntity = companyRepository.findByUsername(authUser.getName()).orElse(null);

        postEntities = getFiltered(type,salary,workTime,authUser,false);
        return postEntities
                .stream()
                .filter(u -> !u.getEmail().equals(companyEntity.getEmail()))
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());

    }



    private List<PostEntity> getFiltered(String type, String salary, String workTime) {

        List<PostEntity> postEntities =null;

        if (!type.equals("")) {
            postEntities = postRepository.findByType(type);
        }
        if (!workTime.equals("")){
            if(postEntities !=null){
                postEntities
                        .stream()
                        .filter(t->t.getWorkTime().equals(workTime))
                        .collect(Collectors.toList());
            }
            else
                postEntities = postRepository.findByWorkTime(workTime);
        }
        if (!salary.equals("")){
            if(postEntities !=null){
                postEntities
                        .stream()
                        .filter(t->t.getSalary().equals(salary))
                        .collect(Collectors.toList());
            }
            else
                postEntities = postRepository.findBySalary(salary);
        }
        if(type.equals("") && workTime.equals("") && salary.equals("")){
            return getJobTitlesForFilter();
        }

        return postEntities;
    }


    private List<PostEntity> getFiltered(String type, String salary, String workTime, Authentication authUser, boolean state){
        CompanyEntity companyEntity = companyRepository.findByUsername(authUser.getName()).orElse(null);

        List<PostEntity> postEntities =null;

        if (!type.equals("")) {
            postEntities = postRepository.findByTypeAndEmail(type, companyEntity.getEmail());
        }
        if (!workTime.equals("")){
            if(postEntities !=null){
                postEntities
                        .stream()
                        .filter(t->t.getWorkTime().equals(workTime))
                        .collect(Collectors.toList());
            }
            else
                postEntities = postRepository.findByWorkTimeAndEmail(workTime, companyEntity.getEmail());
        }
        if (!salary.equals("")){
            if(postEntities !=null){
                postEntities
                        .stream()
                        .filter(t->t.getSalary().equals(salary))
                        .collect(Collectors.toList());
            }
            else
                postEntities = postRepository.findBySalaryAndEmail(salary, companyEntity.getEmail());
        }
        if(type.equals("") && workTime.equals("") && salary.equals("")){
            if(!state){
                return getJobTitlesWithoutCompanyForFilter();
            }
            return getJobTitlesForComapnyForFilter(companyEntity.getUsername());
        }
        return postEntities;
    }
}
