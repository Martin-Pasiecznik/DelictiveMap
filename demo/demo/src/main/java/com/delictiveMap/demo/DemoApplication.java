package com.delictiveMap.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.delictiveMap.demo", "com.example.delinquency"})
@EnableJpaRepositories(basePackages = "com.example.delinquency.repository")
@EntityScan(basePackages = "com.example.delinquency.model")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
