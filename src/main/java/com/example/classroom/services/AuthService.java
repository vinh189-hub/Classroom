package com.example.classroom.services;

import com.example.classroom.app.AuthenticationResponse;
import com.example.classroom.app.Response;
import com.example.classroom.config.JWTService;
import com.example.classroom.dto.AuthenticationRequest;
import com.example.classroom.dto.RegisterRequest;
import com.example.classroom.entities.User;
import com.example.classroom.exceptions.UserExistedException;
import com.example.classroom.repositories.AuthRepository;
import com.example.classroom.token.Token;
import com.example.classroom.token.TokenRepository;
import com.example.classroom.token.TokenType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthRepository authRepository;

    public void register(@Valid  RegisterRequest registerRequest)
    {
        var user = this.authRepository.findByEmail(registerRequest.getEmail());
        logger.info(String.valueOf(user.isPresent()));
        if (user.isPresent()) {
            throw new UserExistedException("user existed");
        }
        var entity = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .status(1)
                .type(1)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        this.authRepository.save(entity);
//        var jwtToken = jwtService.generateToken(entity);
//        logger.info(jwtToken);
//        saveUserToken(entity, jwtToken);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info(request.getEmail());
        try{
            logger.info("Bo may vao duoc day roi con cho dm");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }catch (Exception e){
            logger.info("Day nay con cho");
            logger.info(e.getMessage());
            logger.info("Day nua con con dien");
        }
        var user = authRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
