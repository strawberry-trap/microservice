package com.microservice.batch.service;

import message.domain.Message;
import com.microservice.batch.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMQPublishService {

    final String EXCHANGE_NAME = "microservice.exchange";
    final String ROUTING_KEY = "microservice.rory.#";

    private final MessageRepository messageRepository;

    private final RabbitMQPublishAsyncJobService rabbitMQPublishAsyncJobService;


    @Autowired
    public RabbitMQPublishService(MessageRepository messageRepository, RabbitMQPublishAsyncJobService rabbitMQPublishAsyncJobService) {
        this.messageRepository = messageRepository;
        this.rabbitMQPublishAsyncJobService = rabbitMQPublishAsyncJobService;
    }

    public void pushMQMessages() {

        // get all messages from RDS (blocking call)
        List<Message> messageList = messageRepository.findAllByPublished(false)
                .collectList().block();

        // asynchronously push messages to rabbitMQ
        for (Message message : messageList) {
            rabbitMQPublishAsyncJobService.asyncPublishMQMessage(message, EXCHANGE_NAME, ROUTING_KEY);
        }

        // mark them as published
        messageList.forEach( msg -> msg.setPublished(true));
        messageRepository.saveAll(messageList).subscribe();
        return;
    }
}
