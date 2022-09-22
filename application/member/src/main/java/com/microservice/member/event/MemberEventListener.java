package com.microservice.member.event;

import com.microservice.member.message.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import message.domain.Message;
import message.event.member.MemberCreateEvent;
import message.event.member.MemberEvent;
import message.event.member.MemberGetEvent;
import message.event.member.MemberListEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

/**
 * @author rory.k
 * MemberEventListener class listens spring event, and save event data to T_MESSAGE table
 *
 * annotation @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) is used to seperate business transaction and event transaction,
 * resulting single failure doesn't affect another transaction.
 * */
@Slf4j
@Component
public class MemberEventListener {

    private final MessageRepository messageRepository;

    @Autowired
    public MemberEventListener (MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

//    @Async
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void handleMemberEvent(MemberEvent memberEvent) {
//        messageRepository.save(eventToMessageConverter(memberEvent));
//    }

    @EventListener
    public void handleMemberEvent(MemberCreateEvent memberCreateEvent) {
        // possibly,
        // save message 1
        // save message 2 ...
        messageRepository.save(eventToMessageConverter(memberCreateEvent));
        return;
    }

    @EventListener
    public void handleMemberEvent(MemberGetEvent memberCreateEvent) {
        messageRepository.save(eventToMessageConverter(memberCreateEvent));
        return;
    }

    @EventListener // @Async,  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) 를 사용하지 않아서 싱글 스레드에서 이벤트 발행 - 리슨 진행
    public void handleMemberEvent(MemberListEvent memberListEvent) {

        /**
        // TODO. test

//        String txName = TransactionSynchronizationManager.getCurrentTransactionName();
//        long threadName = Thread.currentThread().getId();
//        log.info("handleMemberEvent tx name : " + TransactionSynchronizationManager.getCurrentTransactionName());
//        log.info("handleMemberEvent thread id : " + Thread.currentThread().getId());
         */

        // use 'subscribe()' at the end, since reactive actions does not execute until the returning publisher is consumed(subscribed).
        messageRepository.save(eventToMessageConverter(memberListEvent)).subscribe();
        return;
    }

    private Message eventToMessageConverter (MemberEvent event) {
        Message message = new Message();
        message.setEventType(event.getMessage());
        message.setPublished(false);
        return message;
    }
}
