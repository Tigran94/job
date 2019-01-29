package clientSide.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "clientSide")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages");
        resolver.setSuffix(".jsp");
        return resolver;

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Bean
//    public MailSender mailSender(){
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost("smtp.gmail.com");
//        javaMailSender.setPort(587);
//        javaMailSender.setUsername("jobproject2019");
//        javaMailSender.setPassword("tigran94!");
//        javaMailSender.setProtocol("smtp");
//        return javaMailSender;
//    }
    @Bean
    public HandlerExceptionResolver errorHandler () {
        SimpleMappingExceptionResolver s =
                new SimpleMappingExceptionResolver();

        //exception to view name mapping
        Properties p = new Properties();
        p.setProperty("java.lang.Exception", "Error");
        s.setExceptionMappings(p);

        //mapping status code with view response.
        s.addStatusCode("Error", 404);

        //setting default error view
        s.setDefaultErrorView("defaultErrorView");
        //setting default status code
        s.setDefaultStatusCode(400);

        return s;
    }

    @Bean
    public BasicDataSource createDataSource(){
        BasicDataSource basicData = new BasicDataSource();
        basicData.setDriverClassName("com.mysql.jdbc.Driver");
        basicData.setUrl("jdbc:mysql://localhost:8889/job");
        basicData.setUsername("Tigran");
        basicData.setPassword("tigran94");
        return basicData;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(createDataSource());
        localSessionFactoryBean.setPackagesToScan("clientSide.entities");
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL57Dialect");
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("format_sql","true");
        properties.setProperty("hbm2ddl.auto","update");

        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }
    @Bean
    public HibernateTransactionManager hibernateTransactionManager(){
        HibernateTransactionManager hibernateTransaction = new HibernateTransactionManager();
        hibernateTransaction.setSessionFactory(sessionFactory().getConfiguration().buildSessionFactory());
        return hibernateTransaction;
    }
}
