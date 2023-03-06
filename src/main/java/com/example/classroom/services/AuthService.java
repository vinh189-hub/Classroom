package com.example.classroom.services;

import com.example.classroom.app.Response;
import com.example.classroom.config.JWTService;
import com.example.classroom.dto.AuthRequest;
import com.example.classroom.dto.RegisterRequest;
import com.example.classroom.entities.User;
import com.example.classroom.exceptions.UserExistedException;
import com.example.classroom.exceptions.UserNotFoundException;
import com.example.classroom.repositories.AuthRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public void register(@Valid  RegisterRequest registerRequest)
    {
        this.logger.info("request",registerRequest);
        var user = this.authRepository.findByEmail(registerRequest.getEmail());
        if (user.isPresent()) {
            throw new UserExistedException("user existed");
        }
        var entity = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .status(1)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        this.authRepository.save(entity);
    }

    public Response authenticate(AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        var user = authRepository.findByEmail(authRequest.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user.getId());
        return new Response("Success", token);
    }

    public User getMe(Long id) {
        return this.authRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user doesn't existed"));
    }

}
