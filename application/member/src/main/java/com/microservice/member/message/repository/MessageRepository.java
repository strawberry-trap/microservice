package com.microservice.member.message.repository;

import message.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepository extends ReactiveCrudRepository<Message, String> {

}
