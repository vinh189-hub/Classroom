package com.example.classroom.dto;

import lombok.Data;

@Data
public class DeleteCommentRequest {
    private long commentId;
    private int classroomId;
    private long postId;
}
