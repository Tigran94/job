package clientSide.dto;

import clientSide.entities.PostEntity;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto {

    public PostDto(PostEntity postEntity){
        this.id = postEntity.getId();
        this.title = postEntity.getTitle();
        this.description = postEntity.getDescription();
        this.type = postEntity.getType();
        this.salary = postEntity.getSalary();
        this.workTime = postEntity.getWorkTime();
        this.email = postEntity.getEmail();
        this.company = postEntity.getCompany();
        this.postDate = postEntity.getPostDate();
        this.expDate = postEntity.getExpirationDate();
    }

    private long id;

    private String title;

    private String description;

    private String type;

    private String salary;

    private String workTime;

    private String email;

    private String company;

    private Date postDate;

    private Date expDate;
}
