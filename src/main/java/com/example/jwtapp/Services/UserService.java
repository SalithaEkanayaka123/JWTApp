package com.example.jwtapp.Services;

import com.example.jwtapp.entities.Users;

public interface UserService {
    Users getUserByName(String name);
}
