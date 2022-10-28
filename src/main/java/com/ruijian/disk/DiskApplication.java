package com.ruijian.disk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
@MapperScan(basePackages = {"com.ruijian.disk.mapper"})
public class DiskApplication implements CommandLineRunner {
    @Value("${file.save.path}")
    private String downloadPath;

    public static void main(String[] args) {
        SpringApplication.run(DiskApplication.class);
    }

    /**
     * 容器初始化后
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        initFolder();
    }

    private void initFolder() {
        final File file = new File(downloadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


}
