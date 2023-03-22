package com.example.classroom.repositories;

import com.example.classroom.entities.Classroom;
import com.example.classroom.entities.User;
import com.example.classroom.entities.UserClassroom;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<UserClassroom,Long> {
    Optional<List<UserClassroom>> findByUserInAndClassroom(List<User> list, Classroom classroom);
}
