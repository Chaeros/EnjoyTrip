package com.ssafy.enjoytrip.domain.attractionboard.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@AllArgsConstructor
public class AttractionBoardLikeResponseDto {
    private long id;
    private String title;
    private String content;
    private int hit;
    private long memberId;
    private int attractionId;
    private String imageUrl;
    private String regdate;
    private int count;
}
