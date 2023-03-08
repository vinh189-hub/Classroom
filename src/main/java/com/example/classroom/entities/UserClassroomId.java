package com.example.classroom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Embeddable
@Data
@RequiredArgsConstructor
public class UserClassroomId implements Serializable {


    @Column(name = "users_id")
    private Long userId;

    @Column(name = "classrooms_id")
    private int classroomId;
}
