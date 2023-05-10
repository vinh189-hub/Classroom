package com.example.classroom.dto;


import lombok.Data;

@Data
public class SubmitRequest {
    private int assignmentId;
    private String submissionDate;
    private int classroomId;
}
