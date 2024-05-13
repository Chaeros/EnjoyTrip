package com.ssafy.enjoytrip.domain.attraction.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionRequestDto {
    private int sidoCode;
    private int gugunCode;
    private int contentTypeId;
    private String keyword;
}
