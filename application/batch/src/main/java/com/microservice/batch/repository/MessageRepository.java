package com.microservice.batch.repository;

import message.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MessageRepository extends ReactiveCrudRepository<Message, String> {
    Flux<Message> findAllByPublished(boolean published);
}
