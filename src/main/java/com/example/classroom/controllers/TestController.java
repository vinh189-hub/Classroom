package com.example.classroom.controllers;

import com.example.classroom.app.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok(new Response<>("Abc", null));
    }
}
