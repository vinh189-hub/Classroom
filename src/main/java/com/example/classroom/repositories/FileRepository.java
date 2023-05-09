package com.example.classroom.repositories;


import com.example.classroom.entities.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<File, Integer> {

}
