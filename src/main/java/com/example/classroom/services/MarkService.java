package com.example.classroom.services;


import com.example.classroom.dto.ScoreRequest;
import com.example.classroom.entities.Assignment;
import com.example.classroom.entities.Mark;
import com.example.classroom.enums.ERole;
import com.example.classroom.repositories.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkService {

    MarkRepository markRepository;

    MemberService memberService;

    AssignmentService assignmentService;

    @Autowired
    public MarkService(
            AssignmentService assignmentService,
            MemberService memberService,
            MarkRepository markRepository)
    {
        this.assignmentService = assignmentService;
        this.memberService = memberService;
        this.markRepository = markRepository;
    }

    public void score(ScoreRequest scoreRequest, long userId){
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, scoreRequest.getClassroomId());
        var userRole = userClassroom.getRole();
        var assignment = this.assignmentService.getAssignmentByIdAndClassroom(scoreRequest.getAssignmentId(), userClassroom.getClassroom());
        var scoreList = scoreRequest.getScore();
        var studentList = this.memberService.getAllUserByClassroomAndRole(userClassroom.getClassroom(), ERole.STUDENT.getValue());
        var listStudentCheck = this.markRepository.findByAssignment(assignment).orElse(null);
        List<Mark> listStudentScored = new ArrayList<>();
        scoreList.forEach(item -> {
            var id = item.get("id").asInt();
            var mark  = item.get("mark").asInt();
            var student = studentList.stream().filter(c -> c.getUser().getId() == id).findAny();
            assert listStudentCheck != null;
            var check = listStudentCheck.stream().filter(c -> c.getUser().getId() == id).findAny();
            if (check.isPresent()){
                this.markRepository.update(check.get().getUser(), check.get().getAssignment(), mark);
            }
            else if(student.isPresent()){
                var data = Mark.builder()
                        .score(mark)
                        .user(student.get().getUser())
                        .assignment(assignment)
                        .build();
                listStudentScored.add(data);
            }
        });
        this.markRepository.saveAll(listStudentScored);
    }

    public List<Mark> getAll(){
        List<Mark> list = new ArrayList<>();
        this.markRepository.findAll().forEach(list::add);
        return  list;
    }

}
