package com.example.classroom.services;

import com.example.classroom.dto.CreateClassroomRequest;
import com.example.classroom.dto.JoinByClassCodeRequest;
import com.example.classroom.dto.TeacherCreateClassroomRequest;
import com.example.classroom.entities.User;
import com.example.classroom.entities.UserClassroom;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.ClassroomNotFoundException;
import com.example.classroom.exceptions.ConflictException;
import com.example.classroom.repositories.ClassroomRepository;
import com.example.classroom.repositories.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class MemberService {

    private ClassroomService classroomService;
    private AuthService authService;
    private MemberRepository memberRepository;

    private ClassroomRepository classroomRepository;

//    @Autowired
//    public MemberService(ClassroomService classroomService,AuthService authService,MemberRepository memberRepository, ) {
//        this.classroomService = classroomService;
//        this.authService = authService;
//        this.memberRepository = memberRepository;
//    }
    @Transactional(rollbackOn = Exception.class)
    public void teacherCreateClassroom(TeacherCreateClassroomRequest request, Long teacherId) throws ConflictException{
        var user = this.authService.getById(teacherId);
        var check = user.getUserClassrooms().stream().filter(item -> item.getClassroom().getName().equals(request.name)).findAny();
        if (check.isPresent()) {
            log.info("teacher has class");
            throw new ConflictException("teacher has class");
        }
        var createClassroomRequest = CreateClassroomRequest.builder()
                .name(request.name)
                .description(request.description)
                .roomName(request.roomName)
                .build();
        var classroom = this.classroomService.create(createClassroomRequest);

        var member = UserClassroom.builder().classroom(classroom).user(user).role(ERole.TEACHER.getValue()).build();
        this.memberRepository.save(member);
    }

    public void studentJoinByClassCode(JoinByClassCodeRequest joinByClassCodeRequest, Long studentId)
        throws ClassroomNotFoundException {
        var  user = this.authService.getById(studentId);
        var checkClassroom = this.classroomRepository.findClassroomByCode(joinByClassCodeRequest.getClassCode()).orElse(null);
        if (checkClassroom == null){
            throw new ClassroomNotFoundException("Classroom not found");
        }
        var check = user.getUserClassrooms().stream().filter(item -> item.getClassroom().getCode().equals(joinByClassCodeRequest.getClassCode())).findAny();
        if (!check.isPresent()) {
            var member = UserClassroom.builder()
                    .classroom(checkClassroom)
                    .user(user)
                    .role(ERole.STUDENT.getValue())
                    .build();
            this.memberRepository.save(member);
        }
    }
}
