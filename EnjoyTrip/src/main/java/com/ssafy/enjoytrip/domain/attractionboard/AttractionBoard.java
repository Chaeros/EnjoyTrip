package com.ssafy.enjoytrip.domain.attractionboard;

import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;
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
    private String imageUrl;
    private String regdate;

    public AttractionBoardResponseDto transferToAttractionBoardResponseDto() {
        return AttractionBoardResponseDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .hit(this.hit)
                .memberId(this.memberId)
                .attractionId(this.attractionId)
                .imageUrl(this.imageUrl)
                .regdate(this.regdate)
                .build();
    }
}
