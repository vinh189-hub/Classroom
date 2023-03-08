package com.example.classroom.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClassroomRequest {
    @NotNull()
    @NotEmpty()
    public String name;

    public String code;

    public String description;


    public String roomName;
}
