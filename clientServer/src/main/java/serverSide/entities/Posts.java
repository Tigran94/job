package serverSide.entities;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title",unique = true,nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "salary")
    private String salary;

    @Column(name = "workTime",nullable = false)
    private String workTime;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    public Posts(){

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

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", salary='" + salary + '\'' +
                ", workTime='" + workTime + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
