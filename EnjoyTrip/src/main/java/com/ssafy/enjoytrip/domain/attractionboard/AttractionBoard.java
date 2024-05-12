package com.ssafy.enjoytrip.domain.attractionboard;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@AllArgsConstructor
public class AttractionBoard {
    private long id;
    private String title;
    private String content;
    private int hit;
    private long memberId;
    private int attractionId;
}
