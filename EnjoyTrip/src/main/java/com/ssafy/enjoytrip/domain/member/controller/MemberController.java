package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.dto.request.MemberSignUpDto;
import com.ssafy.enjoytrip.domain.member.dto.request.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody MemberSignUpDto memberSignUpDto) throws Exception{
        log.info("[sign-up] memberSignUpDto : {}",memberSignUpDto);
        memberService.signUp(memberSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest(){
        return "jwtTest 요청 성공";
    }

    @PostMapping("/oauth2/sign-up")
    public void signUp(@RequestBody MemberUpdateDto memberUpdateDto) throws Exception {
        memberService.updateMember(memberUpdateDto);
    }


}
