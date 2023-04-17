package com.example.classroom.services;

import com.example.classroom.app.Response;
import com.example.classroom.dto.CreatePostRequest;
import com.example.classroom.entities.File;
import com.example.classroom.entities.Post;
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

    @Autowired
    public PostService(
            PostRepository postRepository,
            AuthService authService,
            ClassroomService classroomService,
            FileStorageService fileStorageService,
            FileService fileService
    ){
        this.authService = authService;
        this.postRepository = postRepository;
        this.classroomService = classroomService;
        this.fileService = fileService;
        this.fileStorageService = fileStorageService;
    }

   public Response getAllByClassroom(int classroomId){
        var classroom = this.classroomService.getClassroomById(classroomId);
//        var listPost = this.postRepository.findAllByClassroom(classroom).orElseThrow();
       var listPost = this.postRepository.findAllByClassroom(classroom);
       var listClassroom = this.classroomService.getListClassroom();
        return new Response("success", classroom );
   }

    public void createPost(CreatePostRequest createPostRequest, List<MultipartFile> multipartFiles, Long userId) {
        var description = createPostRequest.getDescription();
        var user = this.authService.getById(userId);
        var classroom = this.classroomService.getClassroomById(createPostRequest.getClassroomId());
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
}
