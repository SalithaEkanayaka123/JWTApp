package com.example.jwtapp.Services;

import com.example.jwtapp.Repository.UserDetailsRepository;
import com.example.jwtapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDetailsRepository.findByUserName(s);
        if (user == null){
            throw new UsernameNotFoundException("User Not Found with the username" + s);
        }
        return user;
    }
}
