package com.example.classroom.services;


import com.example.classroom.app.Response;
import com.example.classroom.dto.CreateClassroomRequest;
import com.example.classroom.dto.RegisterClassroomRequest;
import com.example.classroom.entities.Classroom;
import com.example.classroom.entities.User;
import com.example.classroom.helpers.RandomString;
import com.example.classroom.repositories.AuthRepository;
import com.example.classroom.repositories.ClassroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassroomService {


//    @Autowired
    private AuthRepository authRepository;
//    @Autowired
    private ClassroomRepository classroomRepository;

//    @Autowired
    private RandomString randomString;

    @Autowired
    public ClassroomService(AuthRepository authRepository, ClassroomRepository classroomRepository,RandomString randomString) {
        this.authRepository = authRepository;
        this.classroomRepository = classroomRepository;
        this.randomString = randomString;
    }


    @Transactional(rollbackOn = Exception.class)
    public Response createClassroom(RegisterClassroomRequest registerClassroomRequest, Long id) throws Exception {
//        User user = this.authRepository.findById(id).orElseThrow();
//        UserClassroom userClassroom = new UserClassroom();
//        userClassroom.setRole(2);
//        userClassroom.setUser(user);
//        var classroom = Classroom.builder()
//                .name(registerClassroomRequest.getName())
//                .description(registerClassroomRequest.getDescription())
//                .code(randomString.getAlphaNumericString(10))
//                .theme(null)
//                .build();
//        this.classroomRepository.save(classroom);

//        return new Response("tao lop hoc thanh cong",classroom);
        var classroom = Classroom.builder()
                .name(registerClassroomRequest.getName())
                .build();
        this.classroomRepository.save(classroom);
        log.info("id",classroom.getId());
        throw new Exception("cancel");

//        return null;
    }

    public Classroom create(CreateClassroomRequest request)
    {
        var classroom = Classroom.builder()
                .name(request.name)
                .description(request.description)
                .code(request.code)
                .build();

        this.classroomRepository.save(classroom);
        return classroom;
    }

}
