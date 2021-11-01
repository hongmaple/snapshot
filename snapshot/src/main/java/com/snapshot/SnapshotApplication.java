package com.snapshot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {
        "com.snapshot.mapper"
})
public class SnapshotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnapshotApplication.class, args);
    }

}
