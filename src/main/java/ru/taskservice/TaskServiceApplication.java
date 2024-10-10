package ru.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "ru.taskservice")
public class TaskServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }
}
