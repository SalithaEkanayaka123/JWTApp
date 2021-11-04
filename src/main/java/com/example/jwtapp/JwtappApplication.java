package com.example.jwtapp;

import com.example.jwtapp.Repository.UserDetailsRepository;
import com.example.jwtapp.entities.Authority;
import com.example.jwtapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JwtappApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsRepository userDetailsRepository;


    public static void main(String[] args) {
        SpringApplication.run(JwtappApplication.class, args);
    }

    @PostConstruct
    protected void init(){

        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(createAuthority("USER", "User role"));
        authorityList.add(createAuthority("ADMIN", "Admin role"));

        User user = new User();

        user.setUserName("Salitha");
        user.setFirst_name("Salitha1");
        user.setLast_name("Ekanayaka");
        user.setPassword(passwordEncoder.encode("Salitha"));
        user.setEnabled(true);
        user.setAuthorities(authorityList);//assigning authority list

        System.out.println(user);

        userDetailsRepository.save(user);

    }

    private Authority createAuthority (String roleCode, String roleDescription){
        Authority authority = new Authority();
        authority.setRoleCode(roleCode);
        authority.setRoleDescription(roleDescription);
        return authority;
    }

}
