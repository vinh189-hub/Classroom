package com.example.classroom.controllers;


import com.example.classroom.dto.CommentRequest;
import com.example.classroom.dto.DeleteCommentRequest;
import com.example.classroom.dto.UpdateCommentRequest;
import com.example.classroom.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController extends BaseController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService= commentService;
    }

    @PostMapping("/add-comment")
    public ResponseEntity addComment(@RequestBody @Valid CommentRequest commentRequest){
        var userId = this.getUserId();
        this.commentService.addComment(commentRequest, userId);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/update")
    public ResponseEntity updateComment(@RequestBody @Valid UpdateCommentRequest updateCommentRequest){
        var userId = this.getUserId();
        this.commentService.updateComment(updateCommentRequest, userId);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest){
        var userId = this.getUserId();
        this.commentService.deleteComment(deleteCommentRequest, userId);
        return ResponseEntity.ok("success");
    }
}
