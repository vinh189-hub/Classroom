package com.example.classroom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotEmpty()
    @Email(message =  "phải là email")
    @NotBlank(message = "email is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email khong dung format")
    private String email;

    @NotEmpty()
    @NotNull()
    private String username;

    @NotNull()
    @NotEmpty()
    private String password;
}
