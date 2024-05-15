package com.ssafy.enjoytrip.domain.attractionboardimage.dto.request;

import com.ssafy.enjoytrip.domain.attractionboardimage.AttractionBoardImage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttractionBoardImageAddRequestDto {
    private String image;
    private int attractionBoardId;

    public AttractionBoardImage transferToAttractionBoardImage(){
        return AttractionBoardImage.builder()
                .image(this.image)
                .attractionBoardId(this.attractionBoardId)
                .build();
    }
}
