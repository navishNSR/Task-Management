package com.task.management.app.TaskManagement.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String body){
        try{
            /* For Simple mail */
//            SimpleMailMessage mail = new SimpleMailMessage();
//            mail.setFrom("Task Management App");
//            mail.setTo(to);
//            mail.setSubject(subject);
//            mail.setText(body);

            /* For Customized mail */
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress("navishsingh242892@gmail.com", "TM App - Navish Singh"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, false);

            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Exception while sending mail: "+e.toString());
        }
    }

}
