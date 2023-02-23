package com.example.classroom.repositories;

import com.example.classroom.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface AuthRepository extends CrudRepository<User,Long> {
    @Query(value = "SELECT u FROM User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndUsername(String username, String email);

    Optional<User> findById(Long Id);
}
