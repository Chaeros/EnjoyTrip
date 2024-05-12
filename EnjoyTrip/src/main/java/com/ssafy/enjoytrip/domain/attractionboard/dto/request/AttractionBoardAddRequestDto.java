package com.ssafy.enjoytrip.domain.attractionboard.dto.request;

import com.ssafy.enjoytrip.domain.attractionboard.AttractionBoard;

public class AttractionBoardAddRequestDto {
    private String title;
    private String content;
    private long memberId;
    private int attractionId;

    public AttractionBoard transferToAttractionBoard() {
        return AttractionBoard.builder()
                .title(this.title)
                .content(this.content)
                .memberId(this.memberId)
                .attractionId(this.attractionId)
                .build();
    }
}
