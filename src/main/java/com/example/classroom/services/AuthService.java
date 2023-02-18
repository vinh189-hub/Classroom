package com.example.classroom.services;

import com.example.classroom.dto.RegisterRequest;
import com.example.classroom.entities.User;
import com.example.classroom.exceptions.UserExistedException;
import com.example.classroom.repositories.AuthRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthRepository authRepository;

    public void register(@Valid  RegisterRequest registerRequest)
    {
        this.logger.info("request",registerRequest);
        var user = this.authRepository.findByEmail(registerRequest.getEmail());
        if (user.isPresent()) {
            throw new UserExistedException("user existed");
        }
        var entity = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .status(1)
                .type(1)
                .password(registerRequest.getPassword())
                .build();

        this.authRepository.save(entity);
    }
}
