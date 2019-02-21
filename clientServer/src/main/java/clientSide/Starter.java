package clientSide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class Starter {
    public static void main(String[] args) {
       SpringApplication app = new SpringApplication(Starter.class);
       app.setDefaultProperties(Collections
               .singletonMap("server.host", "192.168.88.40"));
//       SpringApplication.run(Starter.class);

        app.run(args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return BCrypt.hashpw(charSequence.toString(), BCrypt.gensalt(11));
            }
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return BCrypt.checkpw(charSequence.toString(), s);
            }
        };
    }
}
