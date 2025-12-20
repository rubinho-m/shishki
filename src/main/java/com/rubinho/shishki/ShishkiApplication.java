package com.rubinho.shishki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShishkiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShishkiApplication.class, args);
    }

}
