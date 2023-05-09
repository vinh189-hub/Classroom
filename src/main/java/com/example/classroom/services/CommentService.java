package com.example.classroom.services;


import com.example.classroom.dto.CommentRequest;
import com.example.classroom.entities.Comment;
import com.example.classroom.exceptions.PostNotFoundException;
import com.example.classroom.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentService {

    UserService userService;
    AuthService authService;

    ClassroomService classroomService;

    PostService postService;

    CommentRepository commentRepository;

    @Autowired
    public CommentService(UserService userService,
                          AuthService authService,
                          ClassroomService classroomService,
                          PostService postService,
                          CommentRepository commentRepository
                          ){
        this.userService = userService;
        this.authService = authService;
        this.classroomService = classroomService;
        this.postService = postService;
        this.commentRepository = commentRepository;
    }
    public void addComment(CommentRequest commentRequest, Long userId){
        var user = this.authService.getById(userId);
        var classroom = this.classroomService.getClassroomById(commentRequest.getClassroomId());
        var listPost = this.postService.getListPostInClassroom(classroom);
        var postValid = listPost.stream().filter(item -> Objects.equals(item.getId(), commentRequest.getPostId())).toList();
        if(postValid.isEmpty()){
            throw new PostNotFoundException("Post is not found");
        }

        var comment = Comment.builder()
                .post(postValid.get(0))
                .user(user)
                .content(commentRequest.getMessage())
                .build();
        this.commentRepository.save(comment);
    }

}
