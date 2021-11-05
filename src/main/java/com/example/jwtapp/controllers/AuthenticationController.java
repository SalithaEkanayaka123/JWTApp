package com.example.jwtapp.controllers;
import com.example.jwtapp.entities.User;
import com.example.jwtapp.response.LoginResponse;
import org.apache.catalina.startup.UserConfig;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.jwtapp.config.JWTTokenHelper;
import com.example.jwtapp.requests.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jwtTokenHelper;


    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

        //username and passwrod authentication happens here
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        //securty authentication happens here
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        //get a token
        String jwtToken = jwtTokenHelper.generateToken(user.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }
}
