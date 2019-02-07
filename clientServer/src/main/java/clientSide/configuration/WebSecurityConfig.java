package clientSide.configuration;

import clientSide.services.CompanyService;
import clientSide.services.UserService;
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
    private final UserService userService;
    private final CompanyService companyService;

    public WebSecurityConfig(UserService userService, PasswordEncoder passwordEncoder, CompanyService companyService){
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.companyService = companyService;
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
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    @Bean
    public AuthenticationProvider daoAuthenticationProvider2() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(companyService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}