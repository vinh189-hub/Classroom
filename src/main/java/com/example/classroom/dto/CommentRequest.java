package com.example.classroom.dto;

import lombok.Data;

@Data
public class CommentRequest {
    int classroomId;
    Long postId;
    String message;
}
