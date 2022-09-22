package com.microservice.batch.service;

import message.domain.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublishAsyncJobService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublishAsyncJobService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    public void asyncPublishMQMessage(Message message, String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message.getEventType());
        return;
    }
}
