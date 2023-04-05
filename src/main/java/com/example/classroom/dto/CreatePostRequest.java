package com.example.classroom.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreatePostRequest {

    private int classroomId;

    private String description;

//    private List<MultipartFile> fileList;

}


