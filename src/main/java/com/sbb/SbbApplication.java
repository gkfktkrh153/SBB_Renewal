package com.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class SbbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbbApplication.class, args);
    }

}
