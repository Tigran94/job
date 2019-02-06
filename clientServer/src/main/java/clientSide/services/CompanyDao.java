package clientSide.services;

import clientSide.entities.Company;
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
public class CompanyDao implements UserDetailsService {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyDao(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public  void registerCompany(Company companyForReg){
        Company company = new Company();
        company.setEmail(companyForReg.getEmail());
        company.setUsername(companyForReg.getUsername());
        company.setCompanyName(companyForReg.getCompanyName());
        company.setPassword(passwordEncoder.encode(companyForReg.getPassword()));
        company.setActive(true);
        company.setRoleName("ROLE_ADMIN");
        companyRepository.save(company);

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                companyForReg.getCompanyName(),
                                companyForReg.getPassword(),
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")))
                );
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return companyRepository.findByUsername(userName).map(company -> new org.springframework.security.core.userdetails.User(
                company.getUsername(),
                company.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
        ))
                .orElseThrow(() -> new UsernameNotFoundException("Company name not found"));
    }
}
