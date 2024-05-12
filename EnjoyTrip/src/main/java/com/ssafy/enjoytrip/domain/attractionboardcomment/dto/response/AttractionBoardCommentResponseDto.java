package com.ssafy.enjoytrip.domain.attractionboardcomment.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@AllArgsConstructor
public class AttractionBoardCommentResponseDto {
    private int id;
    private String content;
    private int attractionBoardId;
    private int memberId;
}
