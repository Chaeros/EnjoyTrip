package com.ssafy.enjoytrip.domain.attractionboardimage;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@AllArgsConstructor
public class AttractionBoardImage {
    private int id;
    private String image;
    private int attractionBoardId;
}
