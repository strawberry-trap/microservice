package com.microservice.member.web;

import com.microservice.member.repository.MemberRepository;
import com.microservice.member.service.MemberService;
import entity.member.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author rory.k
 * */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = {MemberController.class})
@Import(MemberService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberControllerTest {

    private final String MEMBER_URL = "/member";
    private final String MEMBER_LIST_URL = "/member/list";

    private final String MEMBER_CREATE_URL = "/member/create";

    private final String MEMBER_UPDATE_URL = "/member/update";

    private final String MEMBER_DELETE_URL = "/member/delete";

    private final String APPLICATION_JSON = "application/json";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MemberRepository memberRepository;

    private Mono<Member> monoMember;
    private Flux<Member> fluxMember;

    @BeforeAll
    void setup() {
        this.monoMember = Mono.just(new Member("member-id", "name", "phone_no"));
        this.fluxMember = Flux.fromIterable(Arrays.asList(new Member("id", "name", "phone_no")));
    }

    @Test
    @DisplayName("멤버 단일 조회 테스트")
    void getMember() {

        // setup
        when(memberRepository.findById(anyString()))
                .thenReturn(this.monoMember);

        // exercise
        webTestClient.get()
                .uri(MEMBER_URL + "/{id}", "member-id")
                .header(HttpHeaders.ACCEPT, APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Member.class);

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
        webTestClient.get()
                .uri(MEMBER_LIST_URL)
                .header(HttpHeaders.ACCEPT, APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Member.class);

        // verify
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("멤버 생성 테스트")
    void addMember() {

        // setup
        Member member = new Member("id", "name", "phone_no");
        when(memberRepository.save(any()))
                .thenReturn(Mono.just(member));

        // exercise
        webTestClient.post()
                .uri(MEMBER_CREATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(member))
                .exchange()
                .expectStatus().isCreated();

        // verify
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("멤버 수정 테스트")
    void updateMember() {

        // setup
        Member member = new Member("id", "name", "phone_no");
        when(memberRepository.save(any()))
                .thenReturn(Mono.just(member));

        when(memberRepository.findById(anyString()))
                .thenReturn(Mono.just(member));

        // exercise
        webTestClient.post()
                .uri(MEMBER_UPDATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(member))
                .exchange()
                .expectStatus().isAccepted();

        // verify
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("멤버 삭제 테스트")
    void deleteMember() {

        // setup
        Mono<Void> voidReturn = Mono.empty();
        when(memberRepository.deleteById(anyString()))
                .thenReturn(voidReturn);

        // exercise
        webTestClient.delete()
                .uri(MEMBER_DELETE_URL + "/{id}", "member-id")
                .exchange()
                .expectStatus().isOk();

        // verify
        verify(memberRepository, times(1)).deleteById(anyString());
    }
}