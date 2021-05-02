package com.sucifitz.eshop.cache.kafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * @author szh
 * @date 2021/5/2
 */
public class KafkaMessageProcessor implements Runnable{

    private final KafkaStream<byte[], byte[]> kafkaStream;

    public KafkaMessageProcessor(KafkaStream<byte[], byte[]> kafkaStream) {
        this.kafkaStream = kafkaStream;
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            String message = new String(it.next().message());
        }
    }
}
