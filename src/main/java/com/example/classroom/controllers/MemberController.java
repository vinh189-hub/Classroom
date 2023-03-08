package com.example.classroom.controllers;

import com.example.classroom.app.Response;
import com.example.classroom.dto.TeacherCreateClassroomRequest;
import com.example.classroom.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController  extends BaseController{

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/teacher-register-class")
    public ResponseEntity teacherCreateClass(@RequestBody  TeacherCreateClassroomRequest request)
    {
        var userId = this.getUserId();
        this.memberService.teacherCreateClassroom(request,userId);
        return ResponseEntity.ok(new Response("success",null));
    }
}
