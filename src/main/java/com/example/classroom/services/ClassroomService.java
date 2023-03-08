package com.example.classroom.services;


import com.example.classroom.app.Response;
import com.example.classroom.dto.RegisterClassroomRequest;
import com.example.classroom.entities.Classroom;
import com.example.classroom.entities.User;
import com.example.classroom.entities.UserClassroom;
import com.example.classroom.entities.UserClassroomId;
import com.example.classroom.helpers.RandomString;
import com.example.classroom.repositories.AuthRepository;
import com.example.classroom.repositories.ClassroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.AstListData;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomService {


    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private RandomString randomString;


    @Transactional(rollbackOn = Exception.class)
    public Response createClassroom(RegisterClassroomRequest registerClassroomRequest, Long id){
        User user = this.authRepository.findById(id).orElseThrow();
        UserClassroom userClassroom = new UserClassroom();
        userClassroom.setRole(2);
        userClassroom.setUser(user);
        var classroom = Classroom.builder()
                .name(registerClassroomRequest.getName())
                .description(registerClassroomRequest.getDescription())
                .code(randomString.getAlphaNumericString(10))
                .theme(null)
                .build();
        this.classroomRepository.save(classroom);

        return new Response("tao lop hoc thanh cong",classroom);
    }

}
