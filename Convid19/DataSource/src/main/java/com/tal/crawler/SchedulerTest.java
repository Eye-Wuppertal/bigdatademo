package com.tal.crawler;
/* 
    @TODO: 演示定时任务
    @Author tal
*/

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

//@Component  // 表示将给类交给Spring管理，作为Spring容器中的对象
public class SchedulerTest {
    public static void main(String[] args) {
        // 演示JDK中自带的定时任务
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("每隔一秒执行一次");
            }
        },1000, 1000);
    }

    // 演示SpringBoot中提供的定时任务工具
    // @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    @Scheduled(cron = "0 0 8 * * ?") // 每天八点执行
    public void scheduled(){
        System.out.println("cron每隔一秒执行一次");
    }

}
