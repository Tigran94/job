package clientSide.services;

import clientSide.entities.CompanyEntity;
import clientSide.repositories.CompanyRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CompanyService implements UserDetailsService {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyService(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerCompany(CompanyEntity companyEntityForReg){
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setEmail(companyEntityForReg.getEmail());
        companyEntity.setPhoneNumber(companyEntityForReg.getPhoneNumber());
        companyEntity.setUsername(companyEntityForReg.getUsername());
        companyEntity.setCompanyName(companyEntityForReg.getCompanyName());
        companyEntity.setPassword(passwordEncoder.encode(companyEntityForReg.getPassword()));
        companyEntity.setActive(true);
        companyEntity.setRoleName("ROLE_ADMIN");
        companyRepository.save(companyEntity);

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                companyEntityForReg.getCompanyName(),
                                companyEntityForReg.getPassword(),
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")))
                );
    }

    public void changePassword(Authentication authUser, String newPassword){
        CompanyEntity companyEntity = companyRepository.findByCompanyName(authUser.getName()).orElse(null);
        companyEntity.setPassword(passwordEncoder.encode(newPassword));
        companyRepository.save(companyEntity);
    }

    public CompanyEntity getUser(String username) {
        Optional<CompanyEntity> username1 = companyRepository.findByCompanyName(username);
        CompanyEntity userEntity =  username1.get();
        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return companyRepository.findByUsername(userName).map(company -> new org.springframework.security.core.userdetails.User(
                company.getCompanyName(),
                company.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
        ))
                .orElseThrow(() -> new UsernameNotFoundException("CompanyEntity name not found"));
    }
}
