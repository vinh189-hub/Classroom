package com.example.classroom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "users_classrooms")
public class UserClassroom {

    @EmbeddedId
    private UserClassroomId userClassroomId;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    @MapsId("classroomId")
    @JoinColumn(name = "classrooms_id")
    private Classroom classroom;

    public UserClassroom(User user, int role){
        this.user = user;
        this.role = role;
    }

    private int role;


}
