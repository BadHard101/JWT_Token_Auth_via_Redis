package com.example.studentsretake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentsRetakeApplication {

    public static void main(String[] args) {
        // Это точка входа в приложение Spring Boot.
        // Класс `StudentsRetakeApplication` содержит метод `main`, который запускает приложение.

        SpringApplication.run(StudentsRetakeApplication.class, args);
        // Этот метод инициирует запуск Spring Boot-приложения.
        // Он загружает все конфигурации и начинает обслуживать HTTP-запросы, если  приложение является веб-приложением.
    }
}
