package com.springboot.bizconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BizConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizConnectApplication.class, args);
    }

}
