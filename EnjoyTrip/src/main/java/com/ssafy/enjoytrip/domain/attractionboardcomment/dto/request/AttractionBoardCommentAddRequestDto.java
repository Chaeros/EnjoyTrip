package com.ssafy.enjoytrip.domain.attractionboardcomment.dto.request;

import com.ssafy.enjoytrip.domain.attractionboardcomment.AttractionBoardComment;
import lombok.Builder;
import lombok.Data;

@Data
public class AttractionBoardCommentAddRequestDto {
    private String content;
    private int attractionBoardId;
    private int memberId;

    public AttractionBoardComment transferToAttractionBoardComment() {
        return AttractionBoardComment.builder()
                .content(this.content)
                .attractionBoardId(this.attractionBoardId)
                .memberId(this.memberId)
                .build();
    }
}
