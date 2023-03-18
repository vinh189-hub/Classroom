package com.example.classroom.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException() {
        super();
    }

    public ClassroomNotFoundException(String message) {
        super(message);
    }

}
