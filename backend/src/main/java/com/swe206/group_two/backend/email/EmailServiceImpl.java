package com.swe206.group_two.backend.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(EmailDetails details) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(details.getRecipient());
        message.setSubject(details.getSubject());
        message.setText(details.getBody());

        mailSender.send(message);
    }

    public void sendConfirmationMail(String to, String tournamentName) {
        String subject = "Registration Confirmation";
        String body = "You have been successfully registered in tournament " + tournamentName;

        sendMail(new EmailDetails(to, subject, body));
    }
}
