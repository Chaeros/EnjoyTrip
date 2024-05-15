package com.ssafy.enjoytrip.domain.attractionboard.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
public class AttractionBoardResponseDto {
    private long id;
    private String title;
    private String content;
    private int hit;
    private long memberId;
    private int attractionId;
    private String imageUrl;
    private String regdate;
}
