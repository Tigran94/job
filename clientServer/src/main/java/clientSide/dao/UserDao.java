package clientSide.dao;

import clientSide.entities.User;
import clientSide.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
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

    public  void registerUser(User userForReg){
        User user = new User();
        user.setEmail(userForReg.getEmail());
        user.setFirstName(userForReg.getFirstName());
        user.setLastName(userForReg.getLastName());
        user.setUsername(userForReg.getUsername());
        user.setPassword(passwordEncoder.encode(userForReg.getPassword()));
        user.setRoleName("ROLE_ADMIN");
        user.setActive(true);
        userRepository.save(user);

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                userForReg.getUsername(),
                                userForReg.getPassword(),
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")))
                );
    }

    public String checkIfUserExists(String username, String email){

        ArrayList<String> messages = new ArrayList<String>();

        User user = userRepository.findByUsername(username).orElse(null);
        User user2 = userRepository.findByEmail(email).orElse(null);

        if(user!=null){
            messages.add("This username is already taken");
        }
        if(user2!=null){
            messages.add("This email is already taken");
        }

        return messages.toString().substring(1,messages.toString().length()-1);
    }

    public  void changePassword(Authentication authUser, String newPassword){

        User user = userRepository.findByUsername(authUser.getName()).orElse(null);

        user.setPassword(newPassword);

        userRepository.save(user);
    }

    public void changeFirstName(Authentication authUser,String firstName){
        User user = userRepository.findByUsername(authUser.getName()).orElse(null);

        user.setFirstName(firstName);

        userRepository.save(user);
    }

    public void changeLastName(Authentication authUser,String lastName){
        User user = userRepository.findByUsername(authUser.getName()).orElse(null);
        user.setLastName(lastName);
        userRepository.save(user);
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
