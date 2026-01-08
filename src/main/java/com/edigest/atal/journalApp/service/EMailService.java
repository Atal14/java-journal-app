package com.edigest.atal.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@Slf4j
public class EMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail (String to, String subject, String body) {
        try {
            SimpleMailMessage mail  = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
        } catch(Exception e) {
            log.error("exception while sending mail ", e);
        }
    }
}
