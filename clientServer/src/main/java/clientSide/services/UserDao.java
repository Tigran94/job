package clientSide.services;

import clientSide.entities.Company;
import clientSide.entities.User;
import clientSide.repositories.CompanyRepository;
import clientSide.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserDao implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserDao(PasswordEncoder passwordEncoder, UserRepository userRepository, CompanyRepository companyRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public  void registerUser(User userForReg){
        User user = new User();
        user.setEmail(userForReg.getEmail());
        user.setFirstName(userForReg.getFirstName());
        user.setLastName(userForReg.getLastName());
        user.setUsername(userForReg.getUsername());
        user.setPassword(passwordEncoder.encode(userForReg.getPassword()));
        user.setActive(true);
        user.setRoleName("ROLE_USER");
        userRepository.save(user);

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                userForReg.getUsername(),
                                userForReg.getPassword(),
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                );
    }

    public String checkIfUserExists(String username, String email){

        ArrayList<String> messages = new ArrayList<String>();

        User user = userRepository.findByUsername(username).orElse(null);
        User user2 = userRepository.findByEmail(email).orElse(null);
        Company company = companyRepository.findByUsername(username).orElse(null);
        Company company2 = companyRepository.findByEmail(email).orElse(null);
        if(user!=null || company!=null){
            messages.add("This username is already taken");
        }
        if(user2!=null || company2!=null){
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
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        ))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
