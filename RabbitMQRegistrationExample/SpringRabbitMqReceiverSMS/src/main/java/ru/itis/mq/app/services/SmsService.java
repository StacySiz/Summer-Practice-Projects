package ru.itis.mq.app.services;//package guru.springframework.services;

import java.util.concurrent.ExecutionException;

public interface SmsService {
    boolean sendSMS(String phone) throws ExecutionException, InterruptedException;

}
