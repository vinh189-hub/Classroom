package com.example.classroom.services;

import com.example.classroom.dto.CreateAssignmentRequest;
import com.example.classroom.entities.Assignment;
import com.example.classroom.entities.File;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.ForbiddenException;
import com.example.classroom.helpers.ConvertToUnixTime;
import com.example.classroom.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    AssignmentRepository assignmentRepository;
    MemberService memberService;

    ClassroomService classroomService;
    FileStorageService fileStorageService;
    FileService fileService;

    UserService userService;

    @Autowired
    public AssignmentService(
            UserService userService,
            FileService fileService,
            MemberService memberService,
            ClassroomService classroomService,
            FileStorageService fileStorageService,
            AssignmentRepository assignmentRepository
    ){
        this.userService = userService;
        this.fileService = fileService;
        this.assignmentRepository =  assignmentRepository;
        this.fileStorageService = fileStorageService;
        this.classroomService = classroomService;
        this.memberService = memberService;
    }

    public void create(CreateAssignmentRequest createAssignmentRequest, List<MultipartFile> multipartFiles, long userId)
            throws ForbiddenException
    {
        var classroomId = Integer.parseInt(createAssignmentRequest.getClassroomId());
        var role = this.memberService.getRoleUserClassroom(userId, classroomId);
        if(ERole.STUDENT.getValue() == role){
            throw new ForbiddenException("Khong co quyen");
        }
        var user = this.userService.getById(userId);
        var classroom = this.classroomService.getClassroomById(classroomId);
        var timestamp = ConvertToUnixTime.convertUnixTime(createAssignmentRequest.getDeadlineDate());
        var assignment = Assignment.builder()
                .title(createAssignmentRequest.getTitle())
                .description(createAssignmentRequest.getDescription())
                .score(createAssignmentRequest.getScore())
                .classroom(classroom)
                .user(user)
                .deadlineDate(timestamp)
                .build();
        List<File> list = new ArrayList<>();
        try {
            multipartFiles.forEach((c) -> {
                this.fileStorageService.save(c);
                var url = this.fileStorageService.getUrlFile(c);
                var file = File.builder().url(url).assignment(assignment).build();
                list.add(file);
            });
            this.assignmentRepository.save(assignment);
            this.fileService.saveAll(list);
        } catch (Exception e) {

        }
    }
}
