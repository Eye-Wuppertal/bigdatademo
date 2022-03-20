package com.tal.config;
/* 
    @TODO: 测试Kafka配置
    @Author tal
*/

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestKafka {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testKafkaTemplate() throws Exception {
        kafkaTemplate.send("test",1,"abc");
        Thread.sleep(10000000);
    }

}
