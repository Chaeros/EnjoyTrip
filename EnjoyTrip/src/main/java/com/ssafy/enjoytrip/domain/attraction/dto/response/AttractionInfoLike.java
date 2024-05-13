package com.ssafy.enjoytrip.domain.attraction.dto.response;

import com.ssafy.enjoytrip.domain.attraction.AttractionInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionInfoLike {
    private AttractionInfo AttractionInfo;
    private int likeCnt;
}
