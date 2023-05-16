package com.example.classroom.repositories;


import com.example.classroom.entities.File;
import com.example.classroom.entities.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FileRepository extends CrudRepository<File, Integer> {

    Optional<List<File>> findAllByPost(Post post);

    void deleteAllByPost(Post post);
}
