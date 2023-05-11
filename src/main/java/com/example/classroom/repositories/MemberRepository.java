package com.example.classroom.repositories;

import com.example.classroom.entities.Classroom;
import com.example.classroom.entities.User;
import com.example.classroom.entities.UserClassroom;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
@Transactional
public interface MemberRepository extends CrudRepository<UserClassroom,Long> {
    Optional<List<UserClassroom>> findByUserInAndClassroom(List<User> list, Classroom classroom);

    Optional<UserClassroom> findByUserAndClassroom(User user, Classroom classroom);

    Optional<List<UserClassroom>> findByUser(User user);

    Optional<List<UserClassroom>> findByClassroomAndRole(Classroom classroom, int role);

}
