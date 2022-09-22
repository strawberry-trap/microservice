package com.microservice.member.message;

import com.microservice.member.message.repository.MessageRepository;
import message.constants.CommonMessage;
import message.domain.Message;
import org.springframework.stereotype.Service;

@Service
public class MemberMessageService {

    private final MessageRepository messageRepository;

    public MemberMessageService (MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void readMemberMessage (String id) {
        Message message = new Message(CommonMessage.MemberMessage.MEMBER_GET, "read single member");
        messageRepository.save(message);
        return;
    }
}
