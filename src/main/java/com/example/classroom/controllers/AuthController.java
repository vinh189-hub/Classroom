package com.example.classroom.controllers;

import com.example.classroom.app.Response;
import com.example.classroom.app.ResponseError;
import com.example.classroom.dto.RegisterRequest;
import com.example.classroom.exceptions.ForbiddenException;
import com.example.classroom.exceptions.UserNotFoundException;
import com.example.classroom.services.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    Validator validator;

    @Autowired
    AuthService authService;

    @PostMapping("/api/v1/auth/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest request )  {
        this.authService.register(request);
        return ResponseEntity.ok(new Response("register success",null));
    }
}
