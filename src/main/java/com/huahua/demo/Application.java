package com.huahua.demo;

import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 花云端
 */
@EnableSimbot
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
