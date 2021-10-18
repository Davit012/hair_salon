package com.example.hairsalonrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.hairsaloncommon.*", "com.example.hairsalonrest.*"})
@EnableJpaRepositories(basePackages = {"com.hairsaloncommon.*", "com.example.hairsalonrest.*"})
@EntityScan("com.hairsaloncommon.*")
public class HairSalonRestApplication {


    public static void main(String[] args) {
        SpringApplication.run(HairSalonRestApplication.class, args);
    }

}
