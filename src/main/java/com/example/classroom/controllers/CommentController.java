package com.example.classroom.controllers;


import com.example.classroom.dto.CommentRequest;
import com.example.classroom.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController extends BaseController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService= commentService;
    }

    @PostMapping("/add-comment")
    public ResponseEntity addComment(@RequestBody CommentRequest commentRequest){
        var userId = this.getUserId();
        this.commentService.addComment(commentRequest, userId);
        return ResponseEntity.ok("success");
    }
}
