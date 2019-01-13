package clientSide;

import clientSide.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class Security {
    private static Session session = null;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    static Session getConnection() {
    return sessionFactory.openSession();
}

    public static User login(String username, String password) {
        User loggedInUser = null;
        session = getConnection();
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        Transaction transaction = session.beginTransaction();
        TypedQuery<User> query = session.createQuery("from User h where h.userName = :userName and h.password = :password", User.class);
        query.setParameter("userName", user.getUserName());
        query.setParameter("password", user.getPassword());

        transaction.commit();
        try {
            loggedInUser = query.getSingleResult();

        }catch (NoResultException e){
            //  writer.println(false);
        }
        if(transaction.isActive()){
            session.flush();
        }
        session.close();
        return loggedInUser;
    }

    public static User registerUser(String username, String firstname, String lastname, String email, String password){
        session = getConnection();
        User user = new User();

        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(password);

        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();
        }
        session.close();
        return user;
    }
}

