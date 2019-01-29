package clientSide.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.support.MultipartFilter;


@Configuration
@Import(WebConfig.class)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BasicDataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
                .usersByUsernameQuery("SELECT username, password, isActive AS enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, roleName AS authority FROM users WHERE username=?");

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] adminPages = {"/profile","/addPost","/settings","/settings/*","/profile/*","/uploadFile"};
        String[] freePages = {"/","/login"};
        http.cors().disable().csrf().disable().authorizeRequests().antMatchers(adminPages).hasRole("ADMIN");
//        http.authorizeRequests().antMatchers(freePages).permitAll();
//        http.formLogin().loginPage("login");
        http.addFilter(new DelegatingFilterProxy()).addFilter(new MultipartFilter());
    }
}
