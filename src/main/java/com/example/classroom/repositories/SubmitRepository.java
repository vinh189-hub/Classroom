package com.example.classroom.repositories;

import com.example.classroom.entities.Submit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitRepository extends CrudRepository<Submit, Integer> {
}
