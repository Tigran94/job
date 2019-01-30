package clientSide.dao;

import clientSide.dto.UserDTO;
import clientSide.entities.User;
import clientSide.repositories.UserRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Repository
public class UserDao implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserDao(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

//    public User login(String username, String password) {
//
//        User loggedInUser = null;
//        Session session = sessionFactory.openSession();
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        Transaction transaction = session.beginTransaction();
//        TypedQuery<User> query = session.createQuery("from User h where h.username = :userName and h.password = :password", User.class);
//        query.setParameter("userName", user.getUsername());
//        query.setParameter("password", user.getPassword());
//
//        transaction.commit();
//        try {
//            loggedInUser = query.getSingleResult();
//
//        }catch (NoResultException e){
//        }
//        if(transaction.isActive()){
//            session.flush();
//        }
//        session.close();
//        return loggedInUser;
//    }

    public  void registerUser(UserDTO userDao){
        User user = new User();
        user.setEmail(userDao.getEmail());
        user.setFirstName(userDao.getFirstName());
        user.setLastName(userDao.getLastName());
        user.setUsername(userDao.getUsername());
        user.setPassword(passwordEncoder.encode(userDao.getPassword()));
        userRepository.save(user);

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                userDao.getEmail(),
                                userDao.getPassword(),
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                );
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

        session.close();

        return messages.toString().substring(1,messages.toString().length()-1);
    }

    public  void changePassword(Authentication authUser, String newPassword){
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("from User j where j.username=:username", User.class).setParameter("username",authUser.getName());
        User user1 = (User) query.getSingleResult();


        user1.setPassword(newPassword);

        Transaction transaction = session.beginTransaction();
        session.update(user1);
        transaction.commit();

        if(transaction.isActive()){
            session.flush();
        }
        session.close();
    }

    public void changeFirstName(Authentication authUser,String firstName){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User j where j.username=:username", User.class).setParameter("username",authUser.getName());
        User user1 = (User) query.getSingleResult();

        user1.setFirstName(firstName);

        Transaction transaction = session.beginTransaction();
        session.update(user1);
        transaction.commit();

        if(transaction.isActive()){
            session.flush();
        }
        session.close();
    }

    public void changeLastName(Authentication authUser,String lastName){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User j where j.username=:username", User.class).setParameter("username",authUser.getName());
        User user1 = (User) query.getSingleResult();

        user1.setLastName(lastName);

        Transaction transaction = session.beginTransaction();
        session.update(user1);
        transaction.commit();

        if(transaction.isActive()){
            session.flush();
        }
        session.close();
    }

    public User getUser(String username) {
        Optional<User> username1 = userRepository.findByUsername(username);
        User user =  username1.get();
        return  user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(user -> new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
        ))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
