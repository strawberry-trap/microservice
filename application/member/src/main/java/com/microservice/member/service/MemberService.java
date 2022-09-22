package com.microservice.member.service;

import entity.member.Member;
import com.microservice.member.repository.MemberRepository;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import message.constants.CommonMessage;
import message.event.member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public MemberService (MemberRepository memberRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.memberRepository = memberRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * get member by id
     * @param id
     * */
    public Mono<Member> getMemberById(String id) {
        applicationEventPublisher.publishEvent(new MemberGetEvent(CommonMessage.MemberMessage.MEMBER_GET));
        return memberRepository.findById(id);
    }

    /**
     * get all members
     * */
    // TODO. @Transactional
    public Flux<Member> getMemberList() {

        /**
        // TODO. test with @Transactional added to the method
//        String txName = TransactionSynchronizationManager.getCurrentTransactionName();
//        long threadName = Thread.currentThread().getId();
//        log.info("getMemberList tx name : " + TransactionSynchronizationManager.getCurrentTransactionName());
//        log.info("getMemberList thread id : " + Thread.currentThread().getId());
*/

        // 1. 도메인과 이벤트 발행을 싱글 트랜잭션에 실행
        // 2. 도메인 로직의 간결성 유지를 위해서 어플리케이션 이벤트 발행, 이후 어플리케이션 이벤트를 발행한 것과 같은 스레드에서 RDS에 이벤트 내용의 메세지 저장
        applicationEventPublisher.publishEvent(new MemberListEvent(CommonMessage.MemberMessage.MEMBER_LIST));
        return memberRepository.findAll();
    }

    /**
     * save member
     * @param member DTO
     * */
    public Mono<Member> saveMember(Member member) {
        applicationEventPublisher.publishEvent(new MemberCreateEvent(CommonMessage.MemberMessage.MEMBER_CREATE));
        return memberRepository.save(member);
    }

    /**
     * update member
     * @param member DTO
     * */
    public Mono<Member> updateMember(Member member) {
        applicationEventPublisher.publishEvent(new MemberUpdateEvent(CommonMessage.MemberMessage.MEMBER_UPDATE));
        return memberRepository.findById(member.getId())
                .flatMap(m -> {
                    if(!StringUtil.isNullOrEmpty(member.getName())) m.setName(member.getName());
                    if(!StringUtil.isNullOrEmpty(member.getPhoneNumber())) m.setPhoneNumber(member.getPhoneNumber());
                    return memberRepository.save(m);
                });
    }

    /**
     * delete member
     * @param id member id
     * */
    public Mono<Void> deleteMember(String id) {
        applicationEventPublisher.publishEvent(new MemberDeleteEvent(CommonMessage.MemberMessage.MEMBER_DELETE));
        return memberRepository.deleteById(id);
    }
}
