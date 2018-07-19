package ru.itis.mq.app.services;


import ru.itis.mq.app.commands.RegistrationForm;
import ru.itis.mq.app.domain.User;

import java.util.List;

public interface UserService {

    List<User> listAll();

    User getById(Long id);

    User saveOrUpdate(User user);

    void delete(Long id);

    User saveOrUpdateUserForm(RegistrationForm registrationForm);

    void sendUserMessage(String id);
}
