package com.example.hairsalonweb;

import com.example.hairsalonweb.service.UserService;
import com.hairsaloncommon.model.Gender;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@ComponentScan({"com.hairsaloncommon.*", "com.example.hairsalonweb.*"})
@EnableJpaRepositories(basePackages = {"com.hairsaloncommon.*", "com.example.hairsalonweb.*"})
@EntityScan("com.hairsaloncommon.*")

public class HairSalonWebApplication implements CommandLineRunner {

    @Autowired
     private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(HairSalonWebApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        if (!userService.findByEmail("samveltorosyan29@gmail.com").isPresent()) {

            userService.add(User.builder()
                    .email("samveltorosyan29@gmail.com")
                    .password(passwordEncoder.encode("admin38"))
                    .name("Sam")
                    .surname("Torosyan")
                    .gender(Gender.MALE)
                    .isEmailVerified(true)
                    .userType(UserType.ADMIN)
                    .build());

        }

    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
