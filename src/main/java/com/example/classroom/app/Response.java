package com.example.classroom.app;

import lombok.*;

@AllArgsConstructor
@Data
@Setter
@Getter
public class Response<T> {
    private final String message;
    private  final T data;

//    private Object meta;
}
