package com.example.onebox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OneboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneboxApplication.class, args);
    }

}
