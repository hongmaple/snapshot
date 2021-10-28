package com.withpat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan(basePackages = {
        "com.withpat.mapper"
})
public class WithPatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithPatApplication.class, args);
    }

}
