package com.example.classroom.dto;

import lombok.Data;

@Data
public class DeletePostRequest {
    private long postId;
    private int classroomId;
}
