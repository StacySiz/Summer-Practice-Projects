package ru.itis.mq.app.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationForm {
    Long id;
    String firstName;
    String login;
    String email;
    String phone;
    String password;
}
