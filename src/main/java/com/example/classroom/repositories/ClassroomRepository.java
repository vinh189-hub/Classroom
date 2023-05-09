package com.example.classroom.repositories;

import com.example.classroom.entities.Classroom;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.loadtime.Options;
import org.hibernate.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends CrudRepository<Classroom, Integer> {

    Optional<Classroom> findClassroomByCode(String classCode);

    Optional<Classroom> findById(int id);

}
