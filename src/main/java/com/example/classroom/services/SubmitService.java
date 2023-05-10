package com.example.classroom.services;


import com.example.classroom.dto.SubmitRequest;
import com.example.classroom.entities.File;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SubmitService {

    MemberService memberService;
    FileService fileService;
    FileStorageService fileStorageService;
    UserService userService;
    AssignmentService assignmentService;

    @Autowired
    public SubmitService(
            MemberService memberService,
            FileService fileService,
            FileStorageService fileStorageService,
            UserService userService,
            AssignmentService assignmentService
    ){
        this.memberService = memberService;
        this.fileService = fileService;
        this.fileStorageService = fileStorageService;
        this.userService = userService;
        this.assignmentService = assignmentService;
    }

    public void create(SubmitRequest submitRequest, long userId, List<MultipartFile> fileList) throws ForbiddenException {
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, submitRequest.getClassroomId());
        if(userClassroom.getRole() == ERole.TEACHER.getValue()){
            throw new ForbiddenException("Cai nay danh cho sinh vien thoi");
        }
    }
}
