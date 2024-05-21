package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.attractionboard.AttractionBoard;
import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;
import com.ssafy.enjoytrip.domain.member.Member;
import com.ssafy.enjoytrip.domain.member.Role;
import com.ssafy.enjoytrip.domain.member.dto.request.MemberSignUpDto;
import com.ssafy.enjoytrip.domain.member.dto.request.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.dto.response.MemberResponseDto;
import com.ssafy.enjoytrip.domain.member.exception.MemberException;
import com.ssafy.enjoytrip.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {

        if (memberSignUpDto.getEmail().equals("") || memberSignUpDto.getPassword().equals("")){
            throw new MemberException("ID/PW는 필수로 입력해주세요!");
        }

        if (memberMapper.findMemberByEmail(memberSignUpDto.getEmail())!=null){
            throw new MemberException("이미 존재하는 이메일입니다.");
        }

        if (memberMapper.findMemberByNickname(memberSignUpDto.getNickname())!=null){
            throw new MemberException("이미 존재하는 닉네임입니다.");
        }

        Member member = Member.builder()
                .email(memberSignUpDto.getEmail())
                .password(memberSignUpDto.getPassword())
                .nickname(memberSignUpDto.getNickname())
                .age(memberSignUpDto.getAge())
                .city(memberSignUpDto.getCity())
                .role(Role.USER)
                .build();

        member.passwordEncode(passwordEncoder);
        memberMapper.insertMember(member);
    }

    @Override
    public void updateMember(MemberUpdateDto memberUpdateDto) throws Exception {
        memberMapper.updateMember(memberUpdateDto);
    }

    @Override
    public Optional<MemberResponseDto> getMemberByEmail(String email) throws Exception {
        return Optional.ofNullable(memberMapper.findMemberByEmail(email).transferToMemberResponseDto());
    }

    @Override
    public Optional<MemberResponseDto> getMemberById(long id) throws Exception {
        return Optional.ofNullable(memberMapper.findMemberById(id).transferToMemberResponseDto());
    }

    @Override
    public Optional<List<MemberResponseDto>> getMemberByNicknameKeyword(String keyword) throws Exception {
        List<Member> members = memberMapper.findMembersByNickNameKeyword(keyword);
        List<MemberResponseDto> responseDtos = members.stream()
                .map(Member::transferToMemberResponseDto)
                .collect(Collectors.toList());
        return Optional.ofNullable(responseDtos);
    }

    @Override
    public int updateMemberInfo(Member member) {
        return memberMapper.updateMemberInfo(member);
    }
}
