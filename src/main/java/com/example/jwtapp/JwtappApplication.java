package com.example.jwtapp;

import com.example.jwtapp.entities.Authority;
import com.example.jwtapp.entities.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JwtappApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtappApplication.class, args);
    }


}
