package com.tal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   // 开启SpringBoot程序的定时任务调度
public class DataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSourceApplication.class, args);
    }

}
