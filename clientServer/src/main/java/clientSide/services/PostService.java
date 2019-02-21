package clientSide.services;

import clientSide.dto.JobTitle;
import clientSide.entities.CompanyEntity;
import clientSide.entities.CompanyHistory;
import clientSide.entities.PostEntity;
import clientSide.repositories.CompanyHistoryRepository;
import clientSide.repositories.CompanyRepository;
import clientSide.repositories.PostRepository;

import clientSide.search.PostSearch;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CompanyRepository companyRepository;
    private final CompanyHistoryRepository companyHistoryRepository;

    public PostService(PostRepository postRepository, CompanyRepository companyRepository, CompanyHistoryRepository companyHistoryRepository) {
        this.postRepository = postRepository;
        this.companyRepository = companyRepository;
        this.companyHistoryRepository = companyHistoryRepository;
    }

    public PostEntity addPost(PostEntity postEntity, Authentication authUser) {
        CompanyEntity companyEntity = companyRepository.findByCompanyName(authUser.getName()).orElse(null);
        postEntity.setUser(companyEntity);
        postEntity.setCompany(companyEntity.getCompanyName());
        postEntity.setEmail(companyEntity.getEmail());

        CompanyHistory companyHistory = new CompanyHistory();
        companyHistory.setCompanyName(companyEntity.getCompanyName());
        companyHistory.setUsername(companyEntity.getUsername());
        companyHistory.setAction("I have added " + "\""+postEntity.getTitle()+"\""+" post");
        companyHistory.setCompanyEntity(companyEntity);
        companyHistoryRepository.save(companyHistory);
        postRepository.save(postEntity);
        return postEntity;
    }

    public void deletePost(long id){
        PostEntity postEntity =  postRepository.findById(id).orElse(null);

        CompanyHistory companyHistory = new CompanyHistory();
        companyHistory.setCompanyName(postEntity.getUser().getCompanyName());
        companyHistory.setUsername(postEntity.getUser().getUsername());
        companyHistory.setAction("I have deleted " + "\""+postEntity.getTitle()+"\""+" post");
        companyHistory.setCompanyEntity(postEntity.getUser());
        companyHistoryRepository.save(companyHistory);

        postRepository.delete(postEntity);
    }

    public List<JobTitle> getJobTitles() {
        List<PostEntity> postEntities = postRepository.findAll();

        return postEntities
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }

    public List<JobTitle> getJobTitles(PostSearch postSearch) {
        List<PostEntity> postEntities = postRepository.findAll(postSearch);

        return postEntities
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }

    public List<JobTitle> getJobTitlesForComapny(String companyName) {
        CompanyEntity companyEntity = companyRepository.findByCompanyName(companyName).orElse(null);
        List<PostEntity> postEntities = postRepository.findByEmail(companyEntity.getEmail());

        return postEntities
                .stream()
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }

    public List<JobTitle> getJobTitlesForCompany(PostSearch postSearch,String username) {

        List<PostEntity> postEntities;
        CompanyEntity companyEntity = companyRepository.findByCompanyName(username).orElse(null);
        postEntities = postRepository.findAll(postSearch);

        return postEntities
                .stream()
                .filter(u -> u.getEmail().equals(companyEntity.getEmail()))
                .map(j -> new JobTitle(j.getId(), j.getTitle()))
                .collect(Collectors.toList());
    }


    public PostEntity getJobAnnouncementByIdWithStream(long id) {
        return postRepository.findById(id).orElse(null);
    }
}
