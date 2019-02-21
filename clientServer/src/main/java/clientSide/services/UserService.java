package clientSide.services;

import clientSide.entities.CompanyEntity;
import clientSide.entities.UserEntity;
import clientSide.entities.UserHistory;
import clientSide.repositories.CompanyRepository;
import clientSide.repositories.UserHistoryRepository;
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
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserHistoryRepository userHistoryRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, CompanyRepository companyRepository, UserHistoryRepository userHistoryRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.userHistoryRepository = userHistoryRepository;
    }

    public void registerUser(UserEntity userEntityForReg){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userEntityForReg.getEmail());

        userEntity.setFirstName(userEntityForReg.getFirstName());
        userEntity.setLastName(userEntityForReg.getLastName());

        userEntity.setUsername(userEntityForReg.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userEntityForReg.getPassword()));
        userEntity.setActive(true);
        userEntity.setRoleName("ROLE_USER");

        userRepository.save(userEntity);

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                userEntityForReg.getUsername(),
                                userEntityForReg.getPassword(),
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                );
    }

    public void changePassword(Authentication authUser, String newPassword){
        UserEntity userEntity = userRepository.findByUsername(authUser.getName()).orElse(null);
        UserHistory userHistory = new UserHistory();
        userHistory.setUserName(userEntity.getUsername());
        userHistory.setUserEntity(userEntity);
        userHistory.setAction("I have change my password");

        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userHistoryRepository.save(userHistory);
        userRepository.save(userEntity);
    }

    public void changeFirstName(Authentication authUser,String firstName){
        UserEntity userEntity = userRepository.findByUsername(authUser.getName()).orElse(null);
        UserHistory userHistory = new UserHistory();
        userHistory.setUserName(userEntity.getUsername());
        userHistory.setUserEntity(userEntity);
        userHistory.setAction("I have change my first name from "+userEntity.getFirstName()+ " to "+ firstName);

        userEntity.setFirstName(firstName);


        userHistoryRepository.save(userHistory);
        userRepository.save(userEntity);
    }

    public void changeLastName(Authentication authUser,String lastName){
        UserEntity userEntity = userRepository.findByUsername(authUser.getName()).orElse(null);
        UserHistory userHistory = new UserHistory();
        userHistory.setUserName(userEntity.getUsername());
        userHistory.setUserEntity(userEntity);
        userHistory.setAction("I have change my last name from "+userEntity.getLastName()+ " to "+ lastName);

        userEntity.setLastName(lastName);
        userRepository.save(userEntity);
        userHistoryRepository.save(userHistory);
    }

    public String checkIfUserExists(String username, String email){

        ArrayList<String> messages = new ArrayList<String>();

        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        UserEntity userEntity2 = userRepository.findByEmail(email).orElse(null);
        CompanyEntity companyEntity = companyRepository.findByCompanyName(username).orElse(null);
        CompanyEntity companyEntity2 = companyRepository.findByEmail(email).orElse(null);

        if(userEntity !=null || companyEntity !=null){
            messages.add("This username is already taken");
        }
        if(userEntity2 !=null || companyEntity2 !=null){
            messages.add("This email is already taken");
        }

        return messages.toString().substring(1,messages.toString().length()-1);
    }

    public UserEntity getUser(String username) {
        Optional<UserEntity> username1 = userRepository.findByUsername(username);
        UserEntity userEntity =  username1.get();
        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(userEntity -> new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        ))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
