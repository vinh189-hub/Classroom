package com.example.classroom.repositories;

import com.example.classroom.entities.Assignment;
import com.example.classroom.entities.Classroom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, Integer> {

    Optional<Assignment> findByIdAndClassroom(int id, Classroom classroom);
}
