package com.example.classroom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotEmpty()
    @Email(message =  "phải là email")
    private String email;

    @NotEmpty()
    @NotNull()
    private String username;

    @NotNull()
    @NotEmpty()
    private String password;
}
