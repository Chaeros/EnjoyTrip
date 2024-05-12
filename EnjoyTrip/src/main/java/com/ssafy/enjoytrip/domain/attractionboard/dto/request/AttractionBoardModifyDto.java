package com.ssafy.enjoytrip.domain.attractionboard.dto.request;

import com.ssafy.enjoytrip.domain.attractionboard.AttractionBoard;

public class AttractionBoardModifyDto {
    private long id;
    private String title;
    private String content;

    public AttractionBoard transferToAttractionBoard() {
        return AttractionBoard.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
