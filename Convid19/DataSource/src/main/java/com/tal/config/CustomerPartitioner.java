package com.tal.config;
/* 
    @TODO: 自定义分区器指定分区规则（默认是按照key的hash）
    @Author tal
*/


import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomerPartitioner implements Partitioner {
    // 根据参数按照指定的规则进行分区，返回分区编号即可
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        Integer k = (Integer) key;
        Integer num = cluster.partitionCountForTopic(topic);
        int partition = k % num;
        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
