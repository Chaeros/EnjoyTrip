package com.ssafy.enjoytrip.domain.attractionboardimage.dto.request;

import lombok.Data;

@Data
public class AttractionBoardImageAddRequestDto {
    private String image;
    private int attractionBoardId;
}
