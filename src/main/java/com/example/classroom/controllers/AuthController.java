package com.example.classroom.controllers;

import com.example.classroom.app.AuthenticationResponse;
import com.example.classroom.app.Response;
import com.example.classroom.dto.AuthenticationRequest;
import com.example.classroom.dto.RegisterRequest;
import com.example.classroom.services.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    Validator validator;

    @Autowired
    AuthService authService;

    @PostMapping("/api/v1/auth/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest request )  {
        this.authService.register(request);
        return ResponseEntity.ok(new Response("register success",null));
    }

    @PostMapping("api/v1/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
//       return ResponseEntity.ok(new AuthenticationResponse("abc"));
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
