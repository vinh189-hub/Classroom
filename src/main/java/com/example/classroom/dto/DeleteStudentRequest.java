package com.example.classroom.dto;


import lombok.Data;

@Data
public class DeleteStudentRequest {
    private long id;
    private int classroomId;
}
