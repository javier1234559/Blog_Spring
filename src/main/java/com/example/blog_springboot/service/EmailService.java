package com.example.blog_springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private String code;

    public void sendVerificationCode(String email) {
        code = generateRandomCode();
        String subject = "Forgot Password";
        String text = "Your verification code is: " + code;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public boolean checkVerificationCode(String inputCode) {
        return code != null && code.equals(inputCode);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000000);
        String code = String.format("%06d", randomNumber); //6 digit
        return code;
    }

}
