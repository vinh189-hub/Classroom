package com.example.classroom.services;


import com.example.classroom.app.Response;
import com.example.classroom.helpers.ValidateEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmailWithAttachment(String[] email) throws MessagingException, IOException {
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
