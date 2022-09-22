package com.microservice.member.web;

import dto.common.ResponseDTO;
import entity.member.Member;
import com.microservice.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author rory.k
 *
 * controller can be implemented with 'functional endpoint' style
 * */
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // TODO for async test
    /**
    private final AsyncTaskService asyncTaskService;

    @Autowired
    public MemberController(MemberService memberService, AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
        this.memberService = memberService;
    }

    @GetMapping("/test")
    public Mono<Void> test () {

        List<CompletableFuture> futureList = new ArrayList<>();

        try {
            for (int i=0; i<10; i++) {
                futureList.add(asyncTaskService.task(i));
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        CompletableFuture[] futureArray = futureList.toArray(new CompletableFuture[futureList.size()]);
        CompletableFuture.allOf(futureArray).join();
        System.out.println("all future jobs are done, finish waiting in main thread");

        return Mono.justOrEmpty(null);
    }
     */

    @GetMapping("/{id}")
    public Mono<ResponseDTO<Member>> getMember(@PathVariable String id) {
        return memberService.getMemberById(id).map(ResponseDTO::ok);
    }

    @GetMapping("/list")
    public Flux<Member> getMemberList() {
        return memberService.getMemberList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Member> addMember(@RequestBody Member member) {
        return memberService.saveMember(member);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Member> updateMember(@RequestBody Member member) {
        return memberService.updateMember(member);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteMember(@PathVariable String id) {
        return memberService.deleteMember(id);
    }
}
