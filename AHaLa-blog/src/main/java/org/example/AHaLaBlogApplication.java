package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@MapperScan("org.example.mapper")
@EnableSwagger2
public class AHaLaBlogApplication {
    public static void main(String[] args){
        SpringApplication.run(AHaLaBlogApplication.class,args);
    }
}
