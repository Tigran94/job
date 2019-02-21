package clientSide.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companyHistory")
@Getter
@Setter
@NoArgsConstructor
public class CompanyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "companyName",nullable = false)
    private String companyName;

    @Column(name = "userName",nullable = false)
    private String username;

    @Column(name = "action",nullable = false)
    private String action;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity companyEntity;
}
