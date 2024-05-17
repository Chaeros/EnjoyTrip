package com.ssafy.enjoytrip.domain.attractionboardlike;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttractionBoardLike {
    private int id;
    private long memberId;
    private int attractionBoardId;
}
