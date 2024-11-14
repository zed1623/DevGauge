package com.ljh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:application.yml", "classpath:application-secret.yml"})
public class DevgaugeBackendMaster1Application {

    public static void main(String[] args) {
        SpringApplication.run(DevgaugeBackendMaster1Application.class, args);
    }

}
