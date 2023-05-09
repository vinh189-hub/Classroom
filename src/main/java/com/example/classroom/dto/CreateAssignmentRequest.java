package com.example.classroom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateAssignmentRequest {

    private String classroomId;
    private String title;
    private String description;
    private String score;
    private String deadlineDate;
}
