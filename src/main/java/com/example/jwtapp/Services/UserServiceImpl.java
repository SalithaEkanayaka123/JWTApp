package com.example.jwtapp.Services;

import com.example.jwtapp.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Users getUserByName(String name) {
        Users users = null;
        try{
            users = mongoTemplate.findOne(new Query(where("username").is(name)), Users.class);
        } catch (NullPointerException e){
            System.out.println("User is null");
        }
        return users;
    }
}
