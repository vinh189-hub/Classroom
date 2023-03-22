package com.example.classroom.controllers;


import com.example.classroom.dto.RegisterClassroomRequest;
import com.example.classroom.services.ClassroomService;
import com.example.classroom.services.MyUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping("")
    public ResponseEntity createClassroom(@RequestBody @Valid RegisterClassroomRequest registerClassroomRequest) throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (MyUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(classroomService.createClassroom(registerClassroomRequest, userDetails.getId()));
    }
}
