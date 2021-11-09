package com.example.jwtapp.Repository;

import com.example.jwtapp.entities.ERole;
import com.example.jwtapp.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
