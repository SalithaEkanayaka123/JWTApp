package com.example.jwtapp.entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    //If above dont work use below
//    @Id
//    private String id;


    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

//    @Column(name = "firstName")
//    private String first_name;
//
//    @Column(name = "lastName")
//    private String last_name;

    @Column(name = "email")
    private String email;

//    @Column(name = "phoneNumber")
//    private String phone_number;
//
//    @Column(name = "enabled")
//    private boolean enabled=true;

    //checks whether the user is exsists in the database
    @DBRef
    private Set<Role> roles = new HashSet<>();



    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
