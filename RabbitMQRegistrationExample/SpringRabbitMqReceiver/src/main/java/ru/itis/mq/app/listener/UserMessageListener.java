package ru.itis.mq.app.listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.itis.mq.app.services.EmailService;
import ru.itis.mq.app.utils.UserInfoMessage;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class UserMessageListener {


    private EmailService emailService;

    private static final Logger log = LogManager.getLogger(UserMessageListener.class);

    public UserMessageListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues= "emailQueue")
    public void receiveMessage(String email) throws ExecutionException, InterruptedException {
        //log.info("Received message ", message.toString());
        emailService.sendMail("You successfully registered!","Registration",email);

        log.info("Message processed...");
    }

}
