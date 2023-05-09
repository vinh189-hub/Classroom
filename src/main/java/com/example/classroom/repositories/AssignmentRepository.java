package com.example.classroom.repositories;

import com.example.classroom.entities.Assignment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, Integer> {
}
