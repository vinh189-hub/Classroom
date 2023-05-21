package com.example.classroom.controllers;


import com.example.classroom.app.Response;
import com.example.classroom.dto.CreateAssignmentRequest;
import com.example.classroom.services.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assignment")
public class AssigmentController extends BaseController {

    AssignmentService assignmentService;
    @Autowired
    public AssigmentController(AssignmentService assignmentService){this.assignmentService = assignmentService;}
    @PostMapping("/create")
    public ResponseEntity createAssignment(
            @ModelAttribute @Valid CreateAssignmentRequest createAssignmentRequest,
            @RequestParam("files") List<MultipartFile> multipartFile
    ){
        var userId = this.getUserId();
        this.assignmentService.create(createAssignmentRequest,multipartFile, userId);
        return ResponseEntity.ok(new Response<>("success",null));
    }

}
