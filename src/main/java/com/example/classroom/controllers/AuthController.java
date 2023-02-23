package com.example.classroom.controllers;

import com.example.classroom.app.Response;
import com.example.classroom.dto.AuthRequest;
import com.example.classroom.dto.RegisterRequest;
import com.example.classroom.services.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    Validator validator;

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest request )  {
        this.authService.register(request);
        return ResponseEntity.ok(new Response("register success",null));
    }

    @PostMapping("/authenticate")
    public  ResponseEntity authenticate(@RequestBody AuthRequest authRequest){
        Response response = this.authService.authenticate(authRequest);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity getMe()
    {
        return ResponseEntity.ok(new Response("success",null));
    }
}
