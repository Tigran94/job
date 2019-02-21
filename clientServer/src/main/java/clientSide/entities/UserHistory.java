package clientSide.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "userHistory")
@Getter
@Setter
@NoArgsConstructor
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userName",nullable = false)
    private String userName;


    @Column(name = "action",nullable = false)
    private String action;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
