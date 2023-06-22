package com.wissen.tresata.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DuneWebApiApplication {

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DuneWebApiApplication.class, args);
    }

}
