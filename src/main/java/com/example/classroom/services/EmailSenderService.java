package com.example.classroom.services;


import com.example.classroom.app.Response;
import com.example.classroom.helpers.ValidateEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    private TemplateEngine templateEngine;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Job(name = "send-email-with-attachment", retries = 3)
    public void sendEmailWithAttachment(String[] email) throws Exception {
        MimeMessage msg = javaMailSender.createMimeMessage();
        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);

        Context context = new Context();

        helper.setSubject("Lời mời cùng dạy lớp: ");

        context.setVariable("name", "John");
        String html = templateEngine.process("email.html", context);

        helper.setText(html, true);
//        helper.setText("<h1>Ahihi Đồ Ngốc</h1>", true);
        javaMailSender.send(msg);
    }
}
