package com.example.classroom.dto;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private long postId;
    private String description;
    private int classroomId;
}
