package com.example.classroom.repositories;


import com.example.classroom.entities.User;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<List<User>> findByEmailIn(List<String> list);
}
