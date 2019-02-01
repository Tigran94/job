package clientSide.configuration;

import clientSide.services.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    public WebSecurityConfig(UserDao userDao,PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
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
                .antMatchers("/","/login","/signup").permitAll()
                .antMatchers("/addPost","posts","settings","profile").authenticated()
                .and()
                .authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDao);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
//    @Bean
//    public JavaMailSender mailSender(){
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost("smtp.gmail.com");
//        javaMailSender.setPort(587);
//        javaMailSender.setUsername("springtest94");
//        javaMailSender.setPassword("tigran94");
//        javaMailSender.setProtocol("smtp");
//        return javaMailSender;
//    }
}