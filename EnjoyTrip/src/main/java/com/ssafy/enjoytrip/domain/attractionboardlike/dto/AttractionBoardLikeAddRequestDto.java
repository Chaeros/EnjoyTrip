package com.ssafy.enjoytrip.domain.attractionboardlike.dto;

import lombok.Data;

@Data
public class AttractionBoardLikeAddRequestDto {
    private int attractionBoardId;
    private long memberId;
}
