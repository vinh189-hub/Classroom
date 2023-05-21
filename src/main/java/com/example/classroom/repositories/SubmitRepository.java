package com.example.classroom.repositories;

import com.example.classroom.entities.Assignment;
import com.example.classroom.entities.Submit;
import com.example.classroom.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmitRepository extends CrudRepository<Submit, Integer> {
    Optional<Submit> findByAssignmentAndAndUser(Assignment assignment, User user);
}
