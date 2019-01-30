package clientSide.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Temporal(TemporalType.DATE)
    @Column(name = "postDate",nullable = false)
    private Date postDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "salary")
    private String salary;

    @Column(name = "workTime",nullable = false)
    private String workTime;

    @Column(name="email")
    private String email;

    @Column(name = "company")
    private String company;

    @Temporal(TemporalType.DATE)
    @Column(name = "expirationDate",nullable = false)
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
