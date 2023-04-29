package com.example.classroom.controllers;

import com.example.classroom.app.Response;
import com.example.classroom.services.ClassroomService;
import com.example.classroom.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private ClassroomService classroomService;
    PostService postService;

    public TestController(ClassroomService classroomService, PostService postService){
        this.classroomService = classroomService;
        this.postService = postService;
    }
    @PostMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok(new Response<>("Abc", null));
    }
}
