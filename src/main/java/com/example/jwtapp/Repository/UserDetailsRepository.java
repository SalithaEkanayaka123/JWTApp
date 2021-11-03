package com.example.jwtapp.Repository;

import com.example.jwtapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<User, Long> {
    User findByUserName (String userName);
}
