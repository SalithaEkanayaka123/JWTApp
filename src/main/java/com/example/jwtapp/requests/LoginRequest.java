package com.example.jwtapp.requests;

public class LoginRequest {

    private String UserName;
    private String password;

    public LoginRequest() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
