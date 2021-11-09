package com.example.jwtapp.Services;

import com.example.jwtapp.Repository.UserDetailsRepository;
import com.example.jwtapp.entities.User;
import com.example.jwtapp.entities.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDetailsRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + s));;
        return UserDetailsImpl.build(user);
    }
}
