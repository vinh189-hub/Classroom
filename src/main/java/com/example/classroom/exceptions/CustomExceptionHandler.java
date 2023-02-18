package com.example.classroom.exceptions;

import com.example.classroom.app.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

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
}
