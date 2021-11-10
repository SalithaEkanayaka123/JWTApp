package com.example.jwtapp.controllers;
import com.example.jwtapp.Repository.RoleRepository;
import com.example.jwtapp.Repository.UserDetailsRepository;
import com.example.jwtapp.Services.CustomUserService;
import com.example.jwtapp.entities.Role;
import com.example.jwtapp.entities.Users;
import com.example.jwtapp.requests.LoginRequest;
import com.example.jwtapp.response.JWTResponse;
import com.example.jwtapp.response.MessageResponse;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.jwtapp.config.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jwtTokenHelper;

    @Autowired
    UserDetailsRepository userRepository;


    @Autowired
    PasswordEncoder encoder;


    @Autowired
    CustomUserService customUserService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception ("Password Not Correct", e);
        }

        final UserDetails userDetails = customUserService.loadUserByUsername(loginRequest.getUserName());
        final String jwt = jwtTokenHelper.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new JWTResponse(jwt));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Users users) {
        if (userRepository.existsByUsername(users.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        userRepository.save(users);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }




//    @PostMapping("/auth/login")
//    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {
//
//        //username and passwrod authentication happens here
//        System.out.println(" " + authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
//        //securty authentication happens here
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        User user = (User) authentication.getPrincipal();
//        //get a token
//        String jwtToken = jwtTokenHelper.generateToken(user.getUsername());
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(jwtToken);
//
//        return ResponseEntity.ok(loginResponse);
//    }



}
