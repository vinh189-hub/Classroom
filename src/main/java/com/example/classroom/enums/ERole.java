package com.example.classroom.enums;

public enum ERole {

    TEACHER(1),

    STUDENT(2);

    private int value;

    private ERole(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
