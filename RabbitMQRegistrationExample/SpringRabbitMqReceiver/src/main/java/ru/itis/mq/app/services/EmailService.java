package ru.itis.mq.app.services;

public interface EmailService {
    void sendMail(String text, String subject, String email);
}

