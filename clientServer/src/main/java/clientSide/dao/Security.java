package clientSide.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class Security {
    SessionFactory sessionFactory = getSessionFactory();

    @PostConstruct
    private SessionFactory getSessionFactory() {
        return new Configuration()
                .configure()
                .buildSessionFactory();
    }







}

