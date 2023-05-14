package com.example.classroom.repositories;

import com.example.classroom.entities.Comment;
import com.example.classroom.entities.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {

    void deleteAllByPost(Post post);
}
