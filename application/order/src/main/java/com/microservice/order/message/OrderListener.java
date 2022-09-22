package com.microservice.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {

    @RabbitListener(queues = "microservice.queue")
    public void receiveMessage(final Message message) {

        // logic comes here...
        log.info(message.toString());
    }

}
