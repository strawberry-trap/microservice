package com.microservice.batch.web;

import com.microservice.batch.service.RabbitMQPublishService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message/batch")
public class MessageBatchController {

    private final RabbitMQPublishService rabbitMQPublishService;

    public MessageBatchController (RabbitMQPublishService rabbitMQPublishService) {
        this.rabbitMQPublishService = rabbitMQPublishService;
    }

    @GetMapping("/publish")
    @Scheduled(cron = "0 * * * * ?")
    public void batchPushMQMessages() {
        rabbitMQPublishService.pushMQMessages();
        return;
    }

}
