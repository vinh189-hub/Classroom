package com.example.classroom.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCommentRequest {
    int commentId;
    int classroomId;
    Long postId;

    @NotEmpty()
    @NotNull()
    String message;
}
