package clientSide.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "companyName",unique = true,nullable = false)
    private String companyName;

    @Column(name = "userName",unique = true,nullable = false)
    private String username;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "phoneNumber",unique = true,nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roleName", columnDefinition = "VARCHAR(255) default \"ROLE_ADMIN\"",nullable = false)
    private String roleName;

    @Column(name = "isActive",columnDefinition = "boolean default 1",nullable = false)
    private boolean isActive;

    @OneToMany(targetEntity = CompanyHistory.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "companyHistory_Id")
    private Set<CompanyHistory> companyHistoryId = new HashSet<>();

    @OneToMany(targetEntity = PostEntity.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "company_Id")
    private Set<PostEntity> postEntities = new HashSet<>();
}
