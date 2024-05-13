package com.ssafy.enjoytrip.domain.member.mapper;

import com.ssafy.enjoytrip.domain.member.Member;
import com.ssafy.enjoytrip.domain.member.SocialType;
import com.ssafy.enjoytrip.domain.member.dto.request.MemberLoginDto;
import com.ssafy.enjoytrip.domain.member.dto.request.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.dto.response.MemberResponseDto;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    int insertMember(Member member);
//    MemberResponseDto loginMember(MemberLoginDto member);
//    List<MemberResponseDto> listMember();
//    int updateMember(MemberUpdateDto member);
//    int deleteMember(String id);

    Member findMemberByEmail(String email);

    Member findMemberByNickname(String nickname);

    Member findMemberByRefreshToken(String refreshToken);

    /**
     * 소셜 타입과 소셜의 식별값으로 회원 찾는 메소드
     * 정보 제공을 동의한 순간 DB에 저장해야하지만, 아직 추가 정보(사는 도시, 나이 등)를 입력받지 않았으므로
     * 유저 객체는 DB에 있지만, 추가 정보가 빠진 상태이다.
     * 따라서 추가 정보를 입력받아 회원 가입을 진행할 때 소셜 타입, 식별자로 해당 회원을 찾기 위한 메소드
     */
    Member findMemberBySocialTypeAndSocialId(Map<String, String> map);

    // 새로 발급받은 RefreshToken 정보를 갱신한다.
    void saveAndFlush(Member member);

    // 생성된 User 객체를 DB에 저장 : socialType, socialId, email, role 값만 있는 상태(OAuth)
    Member save(Member member);

    void updateMember(MemberUpdateDto memberUpdateDto);
}
