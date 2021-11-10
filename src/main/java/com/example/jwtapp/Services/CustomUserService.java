package com.example.jwtapp.Services;

import com.example.jwtapp.Repository.UserDetailsRepository;
import com.example.jwtapp.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class CustomUserService implements UserDetailsService {

    private String username;
    private String password;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Users users = userService.getUserByName(userName);
        if(users == null){
            this.username = "";
            this.password = "";
        } else {
            this.username = users.getUsername();
            this.password = users.getPassword();
        }
        return new User(username, password, new ArrayList<>());

    }
}
