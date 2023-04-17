package com.example.classroom.controllers;

import com.example.classroom.app.Response;
import com.example.classroom.dto.CreatePostRequest;
import com.example.classroom.services.PostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController extends BaseController {
    Logger logger = LoggerFactory.getLogger(PostController.class);

    PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
    @PostMapping("/create-post")
    public ResponseEntity createPost(
            @ModelAttribute @Valid CreatePostRequest createPostRequest,
            @RequestParam("files") List<MultipartFile> multipartFile
    ){

        this.postService.createPost(createPostRequest, multipartFile, this.getUserId());
        return ResponseEntity.ok("success");
    }

    @GetMapping("/get-all-post")
    public ResponseEntity getAllPostByClassroom(@RequestParam("classroomId") int classroomId){
        var data = this.postService.getAllByClassroom(classroomId);
        return ResponseEntity.ok(data);
    }
}
