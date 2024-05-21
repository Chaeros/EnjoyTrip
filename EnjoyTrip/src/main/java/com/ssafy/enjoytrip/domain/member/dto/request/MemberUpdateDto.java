package com.ssafy.enjoytrip.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MemberUpdateDto {
    String email;
    String name;
    String nickname;
    String tel;
    String address;
    String confirmEmail;
    String image;
}
