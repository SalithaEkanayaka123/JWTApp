package com.example.jwtapp.entities;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.*;


@Document(collection = "users")
public class Users {

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


    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    public Users(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
