package com.rabbit.rabbit.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQconsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQconsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consume(String message) {
        LOGGER.info(String.format("Recieved message -> %s", message));
    }
}
