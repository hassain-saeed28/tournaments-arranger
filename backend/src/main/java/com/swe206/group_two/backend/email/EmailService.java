package com.swe206.group_two.backend.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public abstract void sendMail(EmailDetails details);
}
