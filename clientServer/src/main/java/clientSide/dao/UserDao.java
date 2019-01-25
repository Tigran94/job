package clientSide.dao;

import clientSide.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

@Repository
public class UserDao{

    @Autowired
    SessionFactory sessionFactory;

    public User login(String username, String password) {

        User loggedInUser = null;
        Session session = sessionFactory.openSession();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Transaction transaction = session.beginTransaction();
        TypedQuery<User> query = session.createQuery("from User h where h.username = :userName and h.password = :password", User.class);
        query.setParameter("userName", user.getUsername());
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

    public  User registerUser(User user){

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();
        }
        session.close();
        return user;
    }

    public String checkIfUserExists(String username, String email){
        Session session = sessionFactory.openSession();

        ArrayList<String> messages = new ArrayList<String>();

        Transaction transaction = session.beginTransaction();
        TypedQuery<User> query1 = session.createQuery("from User h where h.username = :userName", User.class);
        query1.setParameter("userName", username);
        TypedQuery<User> query2 = session.createQuery("from User h where h.email = :email",User.class);
        query2.setParameter("email",email);
        transaction.commit();

        if(query1.getResultList().size()!=0){
            messages.add("This username is already taken");
        }
        if(query2.getResultList().size()!=0){
            messages.add("This email is already taken");
        }


        return messages.toString().substring(1,messages.toString().length()-1);
    }

    public  void changePassword(User user,String newPassword){
        Session session = sessionFactory.openSession();

        user.setPassword(newPassword);

        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();

        if(transaction.isActive()){
            session.flush();
        }
        session.close();
    }
    public void changeFirstName(User user,String firstName){
        Session session = sessionFactory.openSession();

        user.setFirstName(firstName);

        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();

        if(transaction.isActive()){
            session.flush();
        }
        session.close();
    }
    public void changeLastName(User user,String lastName){
        Session session = sessionFactory.openSession();

        user.setLastName(lastName);

        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();

        if(transaction.isActive()){
            session.flush();
        }
        session.close();
    }

    public User getUser(String username) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User j where j.username=:username", User.class).setParameter("username",username);
        return  (User) query.getSingleResult();
    }
}
