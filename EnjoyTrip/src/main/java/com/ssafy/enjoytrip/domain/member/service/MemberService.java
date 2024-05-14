package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.member.dto.request.MemberSignUpDto;
import com.ssafy.enjoytrip.domain.member.dto.request.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.dto.response.MemberResponseDto;

import java.util.Optional;

public interface MemberService {
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception;

    public void updateMember(MemberUpdateDto memberUpdateDto) throws Exception;

    public Optional<MemberResponseDto> getMemberByEmail(String email) throws Exception;
}
