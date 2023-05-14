package com.example.classroom.services;

import com.example.classroom.app.Response;
import com.example.classroom.dto.CreatePostRequest;
import com.example.classroom.dto.DeletePostRequest;
import com.example.classroom.dto.UpdatePostRequest;
import com.example.classroom.entities.Classroom;
import com.example.classroom.entities.File;
import com.example.classroom.entities.Post;
import com.example.classroom.entities.User;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.NoSuchElementException;
import com.example.classroom.exceptions.PostNotFoundException;
import com.example.classroom.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    Logger logger = LoggerFactory.getLogger(PostService.class);

    PostRepository postRepository;
    AuthService authService;
    ClassroomService classroomService;
    FileStorageService fileStorageService;
    FileService fileService;
    MemberService memberService;

    CommentService commentService;

    @Autowired
    public PostService(
            PostRepository postRepository,
            AuthService authService,
            ClassroomService classroomService,
            FileStorageService fileStorageService,
            FileService fileService,
            MemberService memberService
    ) {
        this.memberService = memberService;
        this.authService = authService;
        this.postRepository = postRepository;
        this.classroomService = classroomService;
        this.fileService = fileService;
        this.fileStorageService = fileStorageService;
    }

    @Autowired
    public void setCommentService(CommentService commentService){
        this.commentService = commentService;
    }

    public Response getAllByClassroom(int classroomId) {
        var classroom = this.classroomService.getClassroomById(classroomId);
        var listPost = this.postRepository.findAllByClassroom(classroom);
        var listClassroom = this.classroomService.getListClassroom();
        return new Response("success", classroom);
    }

    public void createPost(CreatePostRequest createPostRequest, List<MultipartFile> multipartFiles, Long userId) {
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, createPostRequest.getClassroomId());
        var description = createPostRequest.getDescription();
        var user = userClassroom.getUser();
        var classroom = userClassroom.getClassroom();
        var post = Post.builder()
                .description(description)
                .user(user)
                .classroom(classroom)
                .build();
        List<File> list = new ArrayList<>();
        try {
            multipartFiles.forEach((c) -> {
                this.fileStorageService.save(c);
                var url = this.fileStorageService.getUrlFile(c);
                var file = File.builder().url(url).post(post).build();
                list.add(file);
            });
            this.postRepository.save(post);
            this.fileService.saveAll(list);
        } catch (Exception e) {
            logger.info("Failed o dayyyyyyyyyyyyyyyy");
        }
    }

    public Post getPostById(Long postId) {
        return this.postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    public List<Post> getListPostInClassroom(Classroom classroom) {
        return this.postRepository.findByClassroom(classroom).orElseThrow();
    }

    public void updatePost(UpdatePostRequest updatePostRequest, long userId, List<MultipartFile> multipartFiles) {
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, updatePostRequest.getClassroomId());
        var post = this.getPostByIdAndUserAndClassroom(updatePostRequest.getPostId(), userClassroom.getUser(), userClassroom.getClassroom());
        var data = Post.builder()
                .id(post.getId())
                .description(updatePostRequest.getDescription())
                .user(userClassroom.getUser())
                .classroom(userClassroom.getClassroom())
                .createdAt(post.getCreatedAt())
                .build();
        this.fileService.deleteAllByPost(post);
        List<File> list = new ArrayList<>();
        try {
            multipartFiles.forEach((c) -> {
                this.fileStorageService.save(c);
                var url = this.fileStorageService.getUrlFile(c);
                var file = File.builder().url(url).post(data).build();
                list.add(file);
            });
            this.postRepository.save(data);
            this.fileService.saveAll(list);
        } catch (Exception e) {

        }
    }

    public void deletePost(DeletePostRequest deletePostRequest, long userId){
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, deletePostRequest.getClassroomId());
        Post post;
        if (userClassroom.getRole() == ERole.TEACHER.getValue()){
            post = this.postRepository.findByIdAndClassroom(deletePostRequest.getPostId(), userClassroom.getClassroom()).orElseThrow(() -> new NoSuchElementException("Not Found Post in this Classroom"));
        }else{
            post = this.getPostByIdAndUserAndClassroom(deletePostRequest.getPostId(), userClassroom.getUser(), userClassroom.getClassroom());
        }
        this.commentService.deleteAllCommentByPost(post);
        this.fileService.deleteAllByPost(post);
        this.delete(deletePostRequest.getPostId());

    }

    private Post getPostByIdAndUserAndClassroom(long postId, User user, Classroom classroom){
        return this.postRepository.findByIdAndUserAndClassroom(postId, user, classroom).orElseThrow(() -> new NoSuchElementException("Not Found Post"));
    }

    private void delete(long id){
        this.postRepository.deleteById(id);
    }
}
