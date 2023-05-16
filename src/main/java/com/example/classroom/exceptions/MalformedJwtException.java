package com.example.classroom.exceptions;

public class MalformedJwtException extends RuntimeException{
    public MalformedJwtException(String message){
        super(message);
    }
}
