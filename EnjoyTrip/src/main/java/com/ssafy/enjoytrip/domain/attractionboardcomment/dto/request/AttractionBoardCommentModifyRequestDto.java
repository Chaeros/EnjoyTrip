package com.ssafy.enjoytrip.domain.attractionboardcomment.dto.request;

import com.ssafy.enjoytrip.domain.attractionboardcomment.AttractionBoardComment;

public class AttractionBoardCommentModifyRequestDto {
    private int id;
    private String content;

    public AttractionBoardComment transferToAttractionBoardComment() {
        return AttractionBoardComment.builder()
                .id(this.id)
                .content(this.content)
                .build();
    }
}
