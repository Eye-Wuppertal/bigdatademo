package com.tal.config;
/* 
    @TODO:  KafkaTemplate 配置类
    @Author tal
*/

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration  // 表示这是一个配置类，可以读取application.properties的内容
public class KafkaProducerConfig {
    @Value("${kafka.bootstrap.servers}")
    private String bootstrap_servers;                    // 服务器地址
    @Value("${kafka.retries_config}")
    private String retries_config;                       // 重试发送消息次数
    @Value("${kafka.batch_size_config}")
    private String batch_size_config;                    // 批量发送的基本单位，默认16384Byte,即16KB
    @Value("${kafka.linger_ms_config}")
    private String linger_ms_config;                     // 批量发送延迟的上限
    @Value("${kafka.buffer_memory_config}")
    private String buffer_memory_config;                 // buffer内存大小

    @Bean   // 表示该方法返回的对象交给spring管理
    public KafkaTemplate getKafkaTemplate(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        config.put(ProducerConfig.RETRIES_CONFIG, retries_config);
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, batch_size_config);
        config.put(ProducerConfig.LINGER_MS_CONFIG, linger_ms_config);
        config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, buffer_memory_config);
        // 设置发送到kafka中的消息的key/value序列化类型，指定为<LocationId: Integer, Value: String>
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 指定自定义分区器
        config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,CustomerPartitioner.class);
        DefaultKafkaProducerFactory producerFactory = new DefaultKafkaProducerFactory(config);
        KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory);
        return kafkaTemplate;
    }

}
