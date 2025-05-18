package com.synchrotech.commandcenter.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String username, String verificationLink) {
        org.springframework.mail.SimpleMailMessage message = new org.springframework.mail.SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verify your email - SynchroTech Command Center");
        message.setText("Hello " + username + ",\n\nPlease verify your email by clicking the link below:\n" + verificationLink + "\n\nIf you did not register, please ignore this email.");
        mailSender.send(message);
    }
} 