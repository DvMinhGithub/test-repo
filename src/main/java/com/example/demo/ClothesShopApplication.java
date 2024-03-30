package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.SpringVersion;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ClothesShopApplication {

    public static void main(String[] args) {
        System.out.println("Spring version: " + SpringVersion.getVersion());
        ApplicationContext applicationContext = SpringApplication.run(ClothesShopApplication.class, args);
    }
}
