package com.ssafy.enjoytrip.domain.member.dto.response;

import com.ssafy.enjoytrip.domain.member.Role;
import com.ssafy.enjoytrip.domain.member.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class MemberResponseDto {
    private Long id;
    private String email; // 이메일
    private String nickname; // 닉네임
    private String image; // 프로필 이미지
    private int age;
    private String city; // 사는 도시
    private Role role;
    private SocialType socialType; // KAKAO, NAVER, GOOGLE
    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)
    private String refreshToken; // 리프레시 토큰
}
