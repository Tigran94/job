package clientSide.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

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

    public Post(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String sallary) {
        this.salary = sallary;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getCompany(){
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setExpirationDate(Date expirationDate){
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate(){
        return this.expirationDate;
    }

    public void setPostDate(Date postDate){
        this.postDate  = postDate;
    }

    public Date getPostDate(){
        return this.postDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", salary='" + salary + '\'' +
                ", workTime='" + workTime + '\'' +
                ", email='" +email+ '\'' +
                '}';
    }



}
