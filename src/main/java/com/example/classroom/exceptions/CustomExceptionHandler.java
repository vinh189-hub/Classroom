package com.example.classroom.exceptions;

import com.example.classroom.app.ResponseError;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException userNotFound, WebRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError(userNotFound.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity handleForbiddenException(ForbiddenException forbiddenException,WebRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseError(forbiddenException.getMessage()));
    }

    @ExceptionHandler(UserExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleUserExistedException(UserExistedException userExistedException)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(userExistedException.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(ex.getMessage());
        return ResponseEntity.badRequest().body(new ResponseError("data not valid"));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity handleAuthenticationException(Exception exception) {
        logger.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseError("forbidden"));
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity handleConflictException(ConflictException exception)
    {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseError(exception.getMessage()));
    }
}
