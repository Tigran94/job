package clientSide;

import clientSide.entities.Post;
import clientSide.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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

    public static void changePassword(User user,String newPassword){
        session = getConnection();

        user.setPassword(newPassword);

        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();

        if(transaction.isActive()){
            session.flush();
        }
        session.close();
    }


    public static Post addPost(String title, String description, String type, String salary, String email, String workTime) {
        session = getConnection();
        Post post = new Post();

        post.setTitle(title);
        post.setDescription(description);
        post.setType(type);
        post.setSalary(salary);
        post.setEmail(email);
        post.setWorkTime(workTime);

        Transaction transaction = session.beginTransaction();
        session.save(post);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();
        }
        session.close();
        return post;
    }
}

