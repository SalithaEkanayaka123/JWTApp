package com.example.jwtapp.Repository;

import com.example.jwtapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

//public interface UserDetailsRepository extends JpaRepository<User, Long> {
//    User findByUserName (String userName);
//}

public interface UserDetailsRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
