package ru.itis.mq.app.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UserInfoMessage implements Serializable {
    private String firstName;
    private String login;
    private String phone;
    private String email;


    @Override
    public String toString() {
        return "UserInfoMessage{" +
                "firstName='" + firstName + '\'' +
                ", login='" + login + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
