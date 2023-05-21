package com.example.classroom.repositories;

import com.example.classroom.entities.Classroom;
import com.example.classroom.entities.Post;
import com.example.classroom.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {


//    @Query(name = "select * from posts join user where classroom_id = :classroom", nativeQuery = true)
    Optional<Set<Post>> findAllByClassroom(Classroom classroom);

    @Query(value = "select * from posts where posts.classroom_id = :id", nativeQuery = true)
    List<Post> findAllA(@Param("id")long classroom_id);

    Optional<List<Post>> findByClassroom(Classroom classroom);

    Optional<Post> findByIdAndUserAndClassroom(long id, User user, Classroom classroom);

    Optional<Post> findByIdAndClassroom(long id, Classroom classroom);



}
