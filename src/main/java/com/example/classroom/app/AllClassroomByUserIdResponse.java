package com.example.classroom.app;

import lombok.Data;
import lombok.Getter;

@Data
public class AllClassroomByUserIdResponse {
    long id;
    String className;
    String description;
    String teacherName;
}
