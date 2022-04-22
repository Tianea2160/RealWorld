package com.jhj.realworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class RealWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(RealWorldApplication.class, args);
    }
}
