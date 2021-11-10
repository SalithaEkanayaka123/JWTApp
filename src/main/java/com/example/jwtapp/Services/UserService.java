package com.example.jwtapp.Services;

import com.example.jwtapp.entities.User;

public interface UserService {
    User getUserByName(String name);
}
