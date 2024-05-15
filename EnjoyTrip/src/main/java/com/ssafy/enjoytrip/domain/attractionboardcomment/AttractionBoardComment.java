package com.ssafy.enjoytrip.domain.attractionboardcomment;

import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.response.AttractionBoardCommentResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@AllArgsConstructor
public class AttractionBoardComment {
    private int id;
    private String content;
    private int attractionBoardId;
    private int memberId;
    private String regdate;

    public AttractionBoardCommentResponseDto transferToAttractionBoardCommentResponseDto() {
        return AttractionBoardCommentResponseDto.builder()
                .id(this.id)
                .content(this.content)
                .memberId(this.memberId)
                .attractionBoardId(this.attractionBoardId)
                .memberId(this.memberId)
                .regdate(this.regdate)
                .build();
    }
}
