package com.activiti.tataainiyo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.activiti.tataainiyo.mapper")
public class TataainiyoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TataainiyoApplication.class, args);
    }

}
