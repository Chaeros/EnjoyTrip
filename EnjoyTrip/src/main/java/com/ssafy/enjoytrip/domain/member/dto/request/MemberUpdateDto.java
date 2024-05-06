package com.ssafy.enjoytrip.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberUpdateDto {
    String email;
    String name;
    String tel;
    String address;
}
