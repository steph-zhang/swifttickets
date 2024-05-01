package com.stephzhang.swifttickets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.stephzhang.swifttickets.mapper")
@SpringBootApplication
public class SwiftticketsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwiftticketsApplication.class, args);
    }

}
