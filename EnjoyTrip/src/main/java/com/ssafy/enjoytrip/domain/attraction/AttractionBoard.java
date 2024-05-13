package com.ssafy.enjoytrip.domain.attraction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionBoard {
    private int id;
    private String title;
    private String content;
    private int hit;
    private int memberId;
    private int attractionId;
}
