package com.example.classroom.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/hello")
    ResponseEntity<String> hello(){
        return new ResponseEntity<>("Hello World", HttpStatus.valueOf("200"));
    }

}
