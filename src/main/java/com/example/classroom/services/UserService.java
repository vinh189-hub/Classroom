package com.example.classroom.services;


import com.example.classroom.entities.User;
import com.example.classroom.exceptions.UserNotFoundException;
import com.example.classroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<User> getByListEmail(List<String> list) throws Exception {
        return this.userRepository.findByEmailIn(list).orElseThrow(() -> new Exception("not found"));
    }

    public User getById(long userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }


}
