package ru.itis.mq.app.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.mq.app.commands.RegistrationForm;
import ru.itis.mq.app.converters.UserFormToUser;
import ru.itis.mq.app.domain.User;
import ru.itis.mq.app.repositories.UserRepository;
import ru.itis.mq.app.utils.UserInfoMessage;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private UserFormToUser userFormToUser;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserFormToUser userFormToUser,
                           RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.userFormToUser = userFormToUser;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add); //fun with Java 8
        return users;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveOrUpdate(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public User saveOrUpdateUserForm(RegistrationForm registrationForm) {
        User savedUser = saveOrUpdate(userFormToUser.convert(registrationForm));

        System.out.println("Saved User Id: " + savedUser.getId());
        return savedUser;
    }

    @Override
    public void sendUserMessage(String id) {
        Map<String, String> actionmap = new HashMap<>();
        actionmap.put("id", id);
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);
        UserInfoMessage newUserInfo = new UserInfoMessage(user.getFirstName(),user.getLogin(),user.getPhone(),user.getEmail());
        log.info("Sending the index request through queue message");
        rabbitTemplate.convertAndSend("registration-exchange", "smsQueue",newUserInfo);
        rabbitTemplate.convertAndSend("registration-exchange","emailQueue", newUserInfo);
    }
}
