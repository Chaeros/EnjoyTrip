package com.ssafy.enjoytrip.domain.attractionboardcomment;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@AllArgsConstructor
public class AttractionBoardComment {
    private long id;
    private String content;
    private long attractionBoardId;
    private long memberId;
}
