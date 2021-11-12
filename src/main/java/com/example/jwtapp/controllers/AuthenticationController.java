package com.example.jwtapp.controllers;
import com.example.jwtapp.Repository.UserDetailsRepository;
import com.example.jwtapp.Services.CustomUserService;
import com.example.jwtapp.Services.UserService;
import com.example.jwtapp.config.JWTTokenHelper;
import com.example.jwtapp.entities.Users;
import com.example.jwtapp.requests.LoginRequest;
import com.example.jwtapp.response.JWTResponse;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
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

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception {
        System.out.println("1 "+ loginRequest.getUsername() + " 2 " + loginRequest.getPassword());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            //SecurityContextHolder.getContext().setAuthentication(authentication);



        } catch (BadCredentialsException e){
            throw new Exception ("Password Not Correct", e);
        }
        final UserDetails userDetails = customUserService.loadUserByUsername(loginRequest.getUsername());

        final String jwt = jwtTokenHelper.generateToken(userDetails.getUsername());
        System.out.println("username is taken " + jwt);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody Users users) {
        System.out.println("1 "+ users.getUsername() + " 2 " + users.getPassword() + " 3 " + users.getEmail() + " 4 " + users.getRole());
        if (userRepository.existsByEmail(users.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        Users user = new Users(users.getUsername(),
                encoder.encode(users.getPassword()),
                users.getEmail(),
                users.getRole()
                );
        userRepository.save(user);
        return new ResponseEntity<>("all ok", HttpStatus.OK);
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
