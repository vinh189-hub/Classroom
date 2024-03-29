package com.example.classroom.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinByClassCodeRequest {

    @NotNull()
    @NotEmpty()
    private String classCode;
}
