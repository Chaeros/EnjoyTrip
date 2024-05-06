package com.ssafy.enjoytrip.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MemberSignUpDto {
    private String email;
    private String password;
    private String nickname;
    private int age;
    private String city;
}
