package com.example.classroom.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class RegisterClassroomRequest {
    @NotEmpty(message = "Bat buoc nhap ten")
    @NotNull(message = "Thieu ten lop hoc")
    private String name;

    private String description;

    private String className;
}
