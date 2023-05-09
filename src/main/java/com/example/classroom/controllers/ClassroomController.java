package com.example.classroom.controllers;


import com.example.classroom.dto.AccessClassroomRequest;
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
public class ClassroomController extends BaseController{

    private ClassroomService classroomService;
    @Autowired
    public ClassroomController(ClassroomService classroomService){
        this.classroomService = classroomService;
    }

    @PostMapping("")
    public ResponseEntity createClassroom(@RequestBody @Valid RegisterClassroomRequest registerClassroomRequest) throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (MyUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(classroomService.createClassroom(registerClassroomRequest, userDetails.getId()));
    }

    @GetMapping("/get-all-classroom")
    public ResponseEntity getAllClassroomByUserId(){
        long userId = this.getUserId();
        return ResponseEntity.ok(this.classroomService.getAllClassroomByUserId(userId));
    }

    @GetMapping("/classroom")
    public ResponseEntity getClassroomById(@RequestBody AccessClassroomRequest accessClassroomRequest){
        return ResponseEntity.ok(this.classroomService.getClassroomById(accessClassroomRequest.getClassroomId()));
    }
}
