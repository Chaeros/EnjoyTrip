package com.ssafy.enjoytrip.domain.attraction.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccomodationRequestDto {
    private int sidoCode;
    private int gugunCode;
    private String keyword;
}
