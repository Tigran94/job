package clientSide;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Security {
    private static Session session = null;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

//    private static List<Users> users = new ArrayList<Users>() {{
//        add(new Users("user1", "password1", "Valodik Valodikyan"));
//        add(new Users("user2", "password2", "adolf Hitler"));
//        add(new Users("user3", "password3", "Joseph Stalin"));
//    }};

//    static Users login(String username, String password) {
//        return users
//                .stream()
//                .filter(u -> u.getUsername().equals(username)
//                        && u.getPassword().equals(password)
//                )
//                .findFirst()
//                .orElse(null);
//    }
    static Session getConnection() {
    return sessionFactory.openSession();
}

    static Users login(String username, String password) {
        Users loggedInUser = null;
        session = getConnection();
        Users users = new Users();
        users.setUserName(username);
        users.setPassword(password);
        Transaction transaction = session.beginTransaction();
        TypedQuery<Users> query = session.createQuery("from Users h where h.userName = :userName and h.password = :password", Users.class);
        query.setParameter("userName", users.getUserName());
        query.setParameter("password", users.getPassword());

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

    static Users registerUser(String username,String firstname,String lastname, String email, String password){
        session = getConnection();
        Users users = new Users();

        users.setFirstName(firstname);
        users.setLastName(lastname);
        users.setUserName(username);
        users.setEmail(email);
        users.setPassword(password);

        Transaction transaction = session.beginTransaction();
        session.save(users);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();
        }
        session.close();
        return users;
    }
}

