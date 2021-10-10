package com.example.hairsalonweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan({"com.hairsaloncommon.*","com.example.hairsalonweb.*"})
@EnableJpaRepositories(basePackages = {"com.hairsaloncommon.*","com.example.hairsalonweb.*"})
@EntityScan("com.hairsaloncommon.*")
public class HairSalonWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairSalonWebApplication.class, args);
    }

}
