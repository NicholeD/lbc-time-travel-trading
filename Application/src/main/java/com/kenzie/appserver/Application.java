package com.kenzie.appserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class Application {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();

    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
