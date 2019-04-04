package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.config")
@MapperScan(basePackages = "com.mapper")
public class Springboot03RzApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot03RzApplication.class, args);
    }

}
