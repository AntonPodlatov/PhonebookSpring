package ru.academit.podlatov.phonebookspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
public class PhonebookSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhonebookSpringApplication.class, args);
    }
}