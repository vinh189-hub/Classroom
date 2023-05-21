package com.example.classroom.repositories;


import com.example.classroom.entities.Assignment;
import com.example.classroom.entities.Mark;
import com.example.classroom.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MarkRepository extends CrudRepository<Mark, Integer> {

    @Modifying
    @Query("update Mark m set m.score = :score where m.user = :user and m.assignment = :assignment")
    void update(@Param(value = "user") User user, @Param(value = "assignment") Assignment assignment, @Param(value = "score") int score);
    Optional<List<Mark>> findByAssignment(Assignment assignment);
}
