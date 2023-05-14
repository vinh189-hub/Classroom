package com.example.classroom.services;


import com.example.classroom.dto.CommentRequest;
import com.example.classroom.dto.DeleteCommentRequest;
import com.example.classroom.dto.UpdateCommentRequest;
import com.example.classroom.entities.Comment;
import com.example.classroom.entities.Post;
import com.example.classroom.entities.User;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.ForbiddenException;
import com.example.classroom.exceptions.NoSuchElementException;
import com.example.classroom.exceptions.PostNotFoundException;
import com.example.classroom.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommentService {

    UserService userService;
    AuthService authService;

    ClassroomService classroomService;

    PostService postService;

    CommentRepository commentRepository;

    MemberService memberService;

    @Autowired
    public CommentService(UserService userService,
                          AuthService authService,
                          ClassroomService classroomService,
//                          PostService postService,
                          CommentRepository commentRepository
                          ){
        this.userService = userService;
        this.authService = authService;
        this.classroomService = classroomService;
//        this.postService = postService;
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setMemberService(MemberService memberService){
        this.memberService = memberService;
    }
    @Autowired
    public void setPostService(PostService postService){
        this.postService = postService;
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

    public void updateComment(UpdateCommentRequest updateCommentRequest, long userId){
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, updateCommentRequest.getClassroomId());
        var post = this.postService.getPostByIdAndClassroom(updateCommentRequest.getPostId(), updateCommentRequest.getClassroomId());
        var comment = this.getCommentByIdAndUser(updateCommentRequest.getCommentId(), userClassroom.getUser(), post);
        var data = Comment.builder()
                .id(comment.getId())
                .content(updateCommentRequest.getMessage())
                .user(userClassroom.getUser())
                .post(post)
                .createdAt(comment.getCreatedAt())
                .build();
        this.commentRepository.save(data);
    }

    public void deleteComment(DeleteCommentRequest deleteCommentRequest, long userId){
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, deleteCommentRequest.getClassroomId());
        if(userClassroom.getRole() == ERole.TEACHER.getValue()){
            this.commentRepository.deleteById(deleteCommentRequest.getCommentId());
        }else{
            var comment = this.commentRepository.findById(deleteCommentRequest.getCommentId()).orElseThrow(() -> new NoSuchElementException("Not Found Comment"));
            if(comment.getUser().getId() == userId){
                this.commentRepository.deleteById(deleteCommentRequest.getCommentId());
            } else throw new ForbiddenException("Forbidden");
        }
    }

    public void deleteAllCommentByPost(Post post){
        this.commentRepository.deleteAllByPost(post);
    }

    public Comment getCommentByIdAndUser(long id, User user, Post post){
        return this.commentRepository.findByIdAndUserAndPost(id, user, post).orElseThrow(() -> new NoSuchElementException("Not Found Comment"));
    }

    public Comment getCommentByIdAndPost(long id, Post post){
        return this.commentRepository.findByIdAndPost(id, post).orElseThrow(() -> new NoSuchElementException("Not Found Comment"));
    }

}
