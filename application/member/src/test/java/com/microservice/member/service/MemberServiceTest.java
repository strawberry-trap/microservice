package com.microservice.member.service;

import com.microservice.member.repository.MemberRepository;
import entity.member.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private MemberService memberService;

    private Mono<Member> monoMember;
    private Flux<Member> fluxMember;

    @BeforeAll
    void setup() {
        this.monoMember = Mono.just(new Member("member-id", "name", "phone_no"));
        this.fluxMember = Flux.fromIterable(Arrays.asList(new Member("member-id", "name", "phone_no")));
        doNothing().when(applicationEventPublisher).publishEvent(any());
    }

    @Test
    @DisplayName("멤버 단일 조회 테스트")
    void getMemberById() {

        // setup
        when(memberRepository.findById(anyString()))
                .thenReturn(this.monoMember);

        // exercise
        Mono<Member> result = memberService.getMemberById(anyString());
        result.subscribe( m -> assertEquals(m.getId(), "member-id"));

        // verify
        verify(memberRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("멤버 목록 조회 테스트")
    void getMemberList() {

        // setup
        when(memberRepository.findAll())
                .thenReturn(this.fluxMember);

        // exercise
        List<Member> members = new ArrayList<>();
        memberService.getMemberList().collectList().subscribe(members::addAll);

        // verify
        assertNotNull(members);
        assertTrue(members.size() == 1);
        assertEquals(members.get(0).getId(), "member-id");
    }

    @Test
    @DisplayName("멤버 생성 테스트")
    void saveMember() {

        // setup
        when(memberRepository.save(any()))
                .thenReturn(this.monoMember);

        // exercise
        Mono<Member> result = memberService.saveMember(this.monoMember.block());

        // verify
        result.subscribe( m -> assertEquals(m.getId(), "member-id"));

        // TODO. occurs twice
        // verify(memberRepository, atMostOnce()).save(any());
    }

    @Test
    @DisplayName("멤버 수정 테스트")
    void updateMember() {

        // setup
        when(memberRepository.findById(anyString()))
                .thenReturn(this.monoMember);

        when(memberRepository.save(any()))
                .thenReturn(this.monoMember);

        // exercise
        Mono<Member> result = memberService.updateMember(this.monoMember.block());

        // verify
        result.subscribe( m -> assertEquals(m.getId(), "member-id"));
        verify(memberRepository, atMostOnce()).save(any());
    }

    @Test
    @DisplayName("멤버 삭제 테스트")
    void deleteMember() {

        // setup
        when(memberRepository.deleteById(anyString())).thenReturn(Mono.empty());

        // exercise
        memberService.deleteMember(anyString());

        // verify
        verify(memberRepository, atMostOnce()).deleteById(anyString());
    }
}