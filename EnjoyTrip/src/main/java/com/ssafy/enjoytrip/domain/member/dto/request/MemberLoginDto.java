package com.ssafy.enjoytrip.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberLoginDto {
    private String email;
    private String password;
}
