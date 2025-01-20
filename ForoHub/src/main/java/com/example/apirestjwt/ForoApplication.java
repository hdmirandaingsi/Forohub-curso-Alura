package com.example.apirestjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.apirestjwt"})
public class ForoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForoApplication.class, args);
    }
}