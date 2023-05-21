package com.example.classroom.services;


import com.example.classroom.app.Response;
import com.example.classroom.dto.CreateClassroomRequest;
import com.example.classroom.dto.DeleteClassroomRequest;
import com.example.classroom.dto.JoinByClassCodeRequest;
import com.example.classroom.dto.RegisterClassroomRequest;
import com.example.classroom.entities.Classroom;
import com.example.classroom.entities.User;
import com.example.classroom.entities.UserClassroom;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.ClassroomNotFoundException;
import com.example.classroom.exceptions.ForbiddenException;
import com.example.classroom.helpers.RandomString;
import com.example.classroom.repositories.AuthRepository;
import com.example.classroom.repositories.ClassroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassroomService {

    private AuthRepository authRepository;
    private ClassroomRepository classroomRepository;

    private RandomString randomString;

    private MemberService memberService;

    @Autowired
    public ClassroomService(AuthRepository authRepository,
                            ClassroomRepository classroomRepository,
                            RandomString randomString,
                            @Lazy MemberService memberService) {
        this.authRepository = authRepository;
        this.classroomRepository = classroomRepository;
        this.randomString = randomString;
        this.memberService = memberService;
    }


    @Transactional(rollbackOn = Exception.class)
    public Response createClassroom(RegisterClassroomRequest registerClassroomRequest, Long id) throws Exception {
        var classroom = Classroom.builder()
                .name(registerClassroomRequest.getName())
                .build();
        this.classroomRepository.save(classroom);
        log.info("id",classroom.getId());
        throw new Exception("cancel");
    }

    public Classroom create(CreateClassroomRequest request)
    {
        var classroom = Classroom.builder()
                .name(request.name)
                .description(request.description)
                .code(this.randomString.getAlphaNumericString(10))
                .build();
        this.classroomRepository.save(classroom);
        return classroom;
    }

    public Classroom getClassroomByCode(JoinByClassCodeRequest joinByClassCodeRequest){
        return this.classroomRepository
                .findClassroomByCode(joinByClassCodeRequest.getClassCode())
                .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));
    }

    public Classroom getClassroomById(int id){
        return this.classroomRepository.findById(id).orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));
    }

    public List<Classroom> getListClassroom(){
        List<Classroom> res = new ArrayList<>();
        this.classroomRepository.findAll().forEach(res::add);
        return res;
    }

    public List<Classroom> getAllClassroomByUserId(long userId){
        return this.memberService.getAllClassroomByUserId(userId);
    }

    public void delete(DeleteClassroomRequest deleteClassroomRequest, long userId){
        var userClassroom = this.memberService.getUserClassroomByUserAndClassroom(userId, deleteClassroomRequest.getClassroomId());
        if(userClassroom.getRole() == ERole.STUDENT.getValue()){
            throw new ForbiddenException("Forbidden");
        }


    }


}
