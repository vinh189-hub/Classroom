package com.example.classroom.controllers;


import com.example.classroom.dto.RegisterClassroomRequest;
import com.example.classroom.services.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/create-classroom")
    public ResponseEntity createClassroom(@RequestBody @Valid RegisterClassroomRequest registerClassroomRequest){
        return ResponseEntity.ok(registerClassroomRequest);
//        return ResponseEntity.ok(classroomService.createClassroom(registerClassroomRequest));
    }
}
