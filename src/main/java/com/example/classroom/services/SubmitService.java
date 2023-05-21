package com.example.classroom.services;


import com.example.classroom.dto.SubmitRequest;
import com.example.classroom.entities.Assignment;
import com.example.classroom.entities.File;
import com.example.classroom.entities.Submit;
import com.example.classroom.entities.User;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.ForbiddenException;
import com.example.classroom.helpers.ConvertToUnixTime;
import com.example.classroom.repositories.SubmitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmitService {

    MemberService memberService;
    FileService fileService;
    FileStorageService fileStorageService;
    UserService userService;
    AssignmentService assignmentService;

    SubmitRepository submitRepository;

    @Autowired
    public SubmitService(
            SubmitRepository submitRepository,
            MemberService memberService,
            FileService fileService,
            FileStorageService fileStorageService,
            UserService userService,
            AssignmentService assignmentService
    ){
        this.submitRepository = submitRepository;
        this.memberService = memberService;
        this.fileService = fileService;
        this.fileStorageService = fileStorageService;
        this.userService = userService;
        this.assignmentService = assignmentService;
    }

    public void create(SubmitRequest submitRequest, long userId, List<MultipartFile> multipartFiles) throws ForbiddenException {
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, submitRequest.getClassroomId());
        var classroom = userClassroom.getClassroom();
        var user = userClassroom.getUser();
        if(userClassroom.getRole() == ERole.TEACHER.getValue()){
            throw new ForbiddenException("Cai nay danh cho sinh vien thoi");
        }
        var assignment = this.assignmentService.getAssignmentByIdAndClassroom(submitRequest.getAssignmentId(), classroom);
        var submitCheck = this.getByAssignmentAndUser(assignment, user);
        if(submitCheck != null){
            throw new ForbiddenException("Khong duoc sua nua");
        }
        var date = ConvertToUnixTime.convertUnixTime(submitRequest.getSubmissionDate());
        var submit = Submit.builder().user(user).assignment(assignment).submissionDate(date).build();
        List<File> list = new ArrayList<>();
        try {
            multipartFiles.forEach((c) -> {
                this.fileStorageService.save(c);
                var url = this.fileStorageService.getUrlFile(c);
                var file = File.builder().url(url).submit(submit).build();
                list.add(file);
            });
            this.submitRepository.save(submit);
            this.fileService.saveAll(list);
        } catch (Exception e) {

        }
    }

    public Submit getByAssignmentAndUser(Assignment assignment, User user){
        return this.submitRepository.findByAssignmentAndAndUser(assignment, user).orElse(null);
    }
}
