package com.example.classroom.controllers;

import com.example.classroom.app.Response;
import com.example.classroom.dto.DeleteStudentRequest;
import com.example.classroom.dto.EmailSenderRequest;
import com.example.classroom.dto.JoinByClassCodeRequest;
import com.example.classroom.dto.TeacherCreateClassroomRequest;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.ConflictException;
import com.example.classroom.services.EmailSenderService;
import com.example.classroom.services.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController  extends BaseController{

    private MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/teacher-register-class")
    public ResponseEntity teacherCreateClass(@RequestBody @Valid TeacherCreateClassroomRequest request)
            throws ConflictException
    {
        var userId = this.getUserId();
        this.memberService.teacherCreateClassroom(request,userId);
        return ResponseEntity.ok(new Response("success",null));
    }
    @PostMapping("/join-by-classcode")
    public ResponseEntity studentJoinByClassCode(@RequestBody @Valid JoinByClassCodeRequest joinByClassCodeRequest)
    {
        var userID = this.getUserId();
        this.memberService.studentJoinByClassCode(joinByClassCodeRequest, userID);
        return ResponseEntity.ok(new Response("success", null));
    }
    @PostMapping("/invite-teacher")
    public ResponseEntity inviteTeacher(@RequestBody @Valid EmailSenderRequest emailSenderRequest)
            throws MessagingException, IOException,Exception {
        this.memberService.inviteTeacherToClass(emailSenderRequest, this.getUserId());
        return ResponseEntity.ok(new Response("success",null));
    }

    @DeleteMapping("/delete-student")
    public ResponseEntity deleteStudent(@RequestBody DeleteStudentRequest deleteStudentRequest){
        var userId = this.getUserId();
        this.memberService.deleteStudent(deleteStudentRequest, userId);
        return ResponseEntity.ok("Success");
    }

}