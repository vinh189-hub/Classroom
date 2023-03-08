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
    private String email;

    @NotEmpty()
    @NotNull()
    private String username;

    @NotNull()
    @NotEmpty()
    @Size( min = 2, message = "it nhat 2 ki tu")
    private String password;
}
