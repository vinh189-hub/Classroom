package com.example.classroom.repositories;

import com.example.classroom.entities.UserClassroom;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<UserClassroom,Long> {
}
