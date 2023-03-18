package com.example.classroom.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class EmailSenderRequest {

//    @Pattern(regexp ="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$" )
//    @Valid()
    @NotNull()
    private String[] email;

    @NotNull()
    private Integer classroomId;
}
