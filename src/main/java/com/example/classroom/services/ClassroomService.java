package com.example.classroom.services;


import com.example.classroom.app.Response;
import com.example.classroom.dto.RegisterClassroomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassroomService {


    public Response createClassroom(RegisterClassroomRequest registerClassroomRequest){
        return new Response("tao lop hoc thanh cong", registerClassroomRequest);
    }

}
