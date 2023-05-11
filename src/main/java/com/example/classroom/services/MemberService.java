package com.example.classroom.services;

import com.example.classroom.app.AllClassroomByUserIdResponse;
import com.example.classroom.app.Response;
import com.example.classroom.dto.CreateClassroomRequest;
import com.example.classroom.dto.EmailSenderRequest;
import com.example.classroom.dto.JoinByClassCodeRequest;
import com.example.classroom.dto.TeacherCreateClassroomRequest;
import com.example.classroom.entities.Classroom;
import com.example.classroom.entities.User;
import com.example.classroom.entities.UserClassroom;
import com.example.classroom.enums.ERole;
import com.example.classroom.exceptions.ClassroomNotFoundException;
import com.example.classroom.exceptions.ConflictException;
import com.example.classroom.exceptions.NoSuchElementException;
import com.example.classroom.helpers.ValidateEmail;
import com.example.classroom.repositories.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
@Slf4j
@AllArgsConstructor
public class MemberService {
    private ClassroomService classroomService;
    private AuthService authService;
    private MemberRepository memberRepository;

    private EmailSenderService emailSenderService;

    private JobScheduler jobScheduler;

    private UserService userService;


    //    @Autowired
//    public MemberService(ClassroomService classroomService,AuthService authService,MemberRepository memberRepository, ) {
//        this.classroomService = classroomService;
//        this.authService = authService;
//        this.memberRepository = memberRepository;
//    }
    @Transactional(rollbackOn = Exception.class)
    public void teacherCreateClassroom(TeacherCreateClassroomRequest request, Long teacherId) throws ConflictException {
        var user = this.authService.getById(teacherId);
        var check = user.getUserClassrooms().stream().filter(item -> item.getClassroom().getName().equals(request.name)).findAny();
        if (check.isPresent()) {
            log.info("teacher has class");
            throw new ConflictException("teacher has class");
        }
        var createClassroomRequest = CreateClassroomRequest.builder().name(request.name).description(request.description).roomName(request.roomName).build();
        var classroom = this.classroomService.create(createClassroomRequest);

        var member = UserClassroom.builder().classroom(classroom).user(user).role(ERole.TEACHER.getValue()).userStatus(true).build();
        this.memberRepository.save(member);
    }

    public void studentJoinByClassCode(JoinByClassCodeRequest joinByClassCodeRequest, Long studentId) throws ClassroomNotFoundException {
        var user = this.authService.getById(studentId);
        var checkClassroom = classroomService.getClassroomByCode(joinByClassCodeRequest);
        var check = user.getUserClassrooms().stream().filter(item -> item.getClassroom().getCode().equals(joinByClassCodeRequest.getClassCode())).findAny();
        if (!check.isPresent()) {
            var member = UserClassroom.builder().classroom(checkClassroom).user(user).role(ERole.STUDENT.getValue()).userStatus(true).build();
            this.memberRepository.save(member);
        }
    }

    public void inviteTeacherToClass(EmailSenderRequest emailSenderRequest, Long id) throws Exception, AuthorizationServiceException {
        var validEmail = Arrays.stream(emailSenderRequest.getEmail()).filter(ValidateEmail::validate);
        var validEmailArr = validEmail.toList();
        var user = this.authService.getMe(id);
        var classroom = this.classroomService.getClassroomById(emailSenderRequest.getClassroomId());

        // lay ra tat ca user ton tai
        var users = this.userService.getByListEmail(validEmailArr);
        var members = this.memberRepository.findByUserInAndClassroom(users, classroom).orElseThrow(() -> new ClassroomNotFoundException("abc"));

        users = users.stream().filter(item -> {
            var exist = members.stream().filter(member -> member.getUser().getId() == item.getId()).findAny();
            return exist.isEmpty();
        }).toList();

        if (users.size() > 0) {
            var listMembersNeedCreate = users.stream().map(item -> UserClassroom.builder().classroom(classroom).user(item).role(ERole.TEACHER.getValue()).userStatus(false).build()).toList();
//            this.sendEmail(users);
            this.memberRepository.saveAll(listMembersNeedCreate);
        }
    }

    public void sendEmail(List<User> users) {
        var emailSender = users.stream().map(item -> item.getEmail()).toArray(String[]::new);
        this.jobScheduler.enqueue(() -> this.emailSenderService.sendEmailWithAttachment(emailSender));
    }

    public int getRoleUserClassroom(Long userId, int classroomId) {
        var user = this.authService.getById(userId);
        var classroom = this.classroomService.getClassroomById(classroomId);
        var res = this.memberRepository.findByUserAndClassroom(user, classroom).orElseThrow(() -> new NoSuchElementException("User not in this Classroom"));
        return res.getRole();
    }

    public UserClassroom getUserClassroomByUserAndClassroom(Long userId, int classroomId){
        var user = this.authService.getById(userId);
        var classroom = this.classroomService.getClassroomById(classroomId);
        return this.memberRepository.findByUserAndClassroom(user, classroom).orElseThrow(() -> new NoSuchElementException("User not in this Classroom"));
    }

    public List<Classroom> getAllClassroomByUserId(long userId){
        List<Classroom> listClassroom = new ArrayList<>();
        var user = this.authService.getById(userId);
        var data = this.memberRepository.findByUser(user).orElseThrow();
        data.forEach(item -> listClassroom.add(item.getClassroom()));
        return listClassroom;
    }

    public List<UserClassroom> getAllUserByClassroomAndRole(Classroom classroom, int role){
       return this.memberRepository.findByClassroomAndRole(classroom, role).orElse(null);
    }
}
