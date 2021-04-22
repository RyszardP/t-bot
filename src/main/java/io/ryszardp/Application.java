package io.ryszardp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // Здесь код написан по заветам
        // https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-spring-boot-starter
        ApiContextInitializer.init();

        SpringApplication.run(Application.class, args);
    }
}
