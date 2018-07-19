package ru.itis.mq.app.listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.itis.mq.app.services.SmsService;

import java.util.concurrent.ExecutionException;

@Component
public class UserMessageListener {


    private SmsService smsService;

    private static final Logger log = LogManager.getLogger(UserMessageListener.class);

    public UserMessageListener(SmsService smsService) {
        this.smsService = smsService;
    }

    @RabbitListener(queues= "smsQueue")
    public void receiveMessage(String phone) throws ExecutionException, InterruptedException {
        //log.info("Received message ", message.toString());
        smsService.sendSMS(phone);

        log.info("Sms) processed...");
    }

}
