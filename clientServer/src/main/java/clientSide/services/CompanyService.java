package clientSide.services;

import clientSide.entities.CompanyEntity;
import clientSide.repositories.CompanyRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CompanyService implements UserDetailsService {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyService(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public  void registerCompany(CompanyEntity companyEntityForReg){
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setEmail(companyEntityForReg.getEmail());
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
