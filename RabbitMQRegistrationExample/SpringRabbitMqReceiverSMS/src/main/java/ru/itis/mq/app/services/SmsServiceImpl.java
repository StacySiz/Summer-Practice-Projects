package ru.itis.mq.app.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class SmsServiceImpl implements SmsService {
    @Value("${sms.aero.user}")
    private String smsAeroLogin;

    @Value("${sms.aero.password}")
    private String smsAeroPassword;

    @Value("${sms.aero.from}")
    private String smsAeroFrom;

    @Value("${sms.aero.type}")
    private String smsAeroType;

    @Value("${sms.aero.url}")
    private String smsAeroUri;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Override
    @Transactional
    public boolean sendSMS(String phone) throws ExecutionException, InterruptedException {

        Future<Boolean> result =  this.executorService.submit(() ->{

            RestTemplate restTemplate = new RestTemplate();

            StringBuilder sb = new StringBuilder();

            sb.append(this.smsAeroUri)
                    .append("?user=").append(this.smsAeroLogin)
                    .append("&password=").append(this.smsAeroPassword)
                    .append("&to=").append(phone)
                    .append("&text=").append("Congratulation! Now you are a member of Stacy's website!")
                    .append("&from=").append(this.smsAeroFrom)
                    .append("&type=").append(this.smsAeroType);

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(sb.toString(), String.class);
            log.info(responseEntity.getBody());
            return responseEntity.getBody().contains("accepted");

        });

        return result.get();

    }
}
