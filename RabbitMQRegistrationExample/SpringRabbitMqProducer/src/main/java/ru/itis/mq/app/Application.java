package ru.itis.mq.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan("ru.itis.mq.app")
@EnableJpaRepositories(basePackages = "ru.itis.mq.app.repositories")
@EntityScan(basePackages = "ru.itis.mq.app.domain")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
