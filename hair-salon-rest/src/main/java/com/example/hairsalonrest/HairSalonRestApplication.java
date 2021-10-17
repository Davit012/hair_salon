package com.example.hairsalonrest;

import com.example.hairsalonrest.repository.UserRepository;
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
@ComponentScan({"com.hairsaloncommon.*", "com.example.hairsalonrest.*"})
@EnableJpaRepositories(basePackages = {"com.hairsaloncommon.*", "com.example.hairsalonrest.*"})
@EntityScan("com.hairsaloncommon.*")
public class HairSalonRestApplication {


    public static void main(String[] args) {
        SpringApplication.run(HairSalonRestApplication.class, args);
    }

}
