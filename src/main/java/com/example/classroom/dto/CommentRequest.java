package com.example.classroom.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {
    int classroomId;
    Long postId;

    @NotNull()
    @NotEmpty()
    String message;
}
