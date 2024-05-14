package com.ssafy.enjoytrip.domain.attractionboard.dto.response;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class AttractionBoardResponseDto {
    private long id;
    private String title;
    private String content;
    private int hit;
    private long memberId;
    private int attractionId;
    private String imageUrl;
}
