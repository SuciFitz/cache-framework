package com.sucifitz.eshop.cache.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Kafka 消费者
 *
 * @author szh
 * @date 2021/4/27
 */
public class KafkaConsumer implements Runnable {

    private final ConsumerConnector connector;

    private final String topic;

    public KafkaConsumer(String topic) {
        connector = Consumer.createJavaConsumerConnector(createConsumerConfig());
        this.topic = topic;
    }

    /**
     * 创建 kafka consumer config
     *
     * @return ConsumerConfig
     */
    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "8.131.90.159:2181,8.131.90.159:2182,8.131.90.159:2183");
        props.put("group.id", "eshop-cache-group");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }

    @Override
    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
                connector.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        for (final KafkaStream<byte[], byte[]> stream : streams) {
            new Thread(new KafkaMessageProcessor(stream)).start();
        }
    }
}
