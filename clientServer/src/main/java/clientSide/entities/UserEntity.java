package clientSide.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "userName",unique = true,nullable = false)
    private String username;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roleName", columnDefinition = "VARCHAR(255) default \"ROLE_USER\"",nullable = false)
    private String roleName;

    @Column(name = "isActive",columnDefinition = "boolean default 1",nullable = false)
    private boolean isActive;

    @OneToMany(targetEntity = PostEntity.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_Id")
    private Set<PostEntity> postEntities = new HashSet<>();

}
