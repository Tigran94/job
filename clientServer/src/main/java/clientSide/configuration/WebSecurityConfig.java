package clientSide.configuration;

import clientSide.services.CompanyDao;
import clientSide.services.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final CompanyDao companyDao;

    public WebSecurityConfig(UserDao userDao, PasswordEncoder passwordEncoder, CompanyDao companyDao){
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.companyDao = companyDao;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .authorizeRequests()
                .antMatchers("/","/login","/signup","/userSignup","/companySignup").permitAll()
                .antMatchers("/addPost","/profile","/profile/*").hasRole("ADMIN")
                .antMatchers("/apply").hasRole("USER")
                .antMatchers("/posts","/settings","/settings/*").authenticated()
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(daoAuthenticationProvider2())
                .sessionManagement().maximumSessions(1)
                .sessionRegistry(sessionRegistry());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDao);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    @Bean
    public AuthenticationProvider daoAuthenticationProvider2() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(companyDao);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}