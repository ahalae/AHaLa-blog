package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("org.example.mapper")
public class AHaLaBlogApplication {
    public static void main(String[] args){
        SpringApplication.run(AHaLaBlogApplication.class,args);
    }
}
