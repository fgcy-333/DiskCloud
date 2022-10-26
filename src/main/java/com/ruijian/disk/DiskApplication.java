package com.ruijian.disk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.ruijian.disk.mapper"})
public class DiskApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiskApplication.class);
    }
}
