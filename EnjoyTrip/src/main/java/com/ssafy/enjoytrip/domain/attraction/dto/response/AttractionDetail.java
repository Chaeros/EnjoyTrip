package com.ssafy.enjoytrip.domain.attraction.dto.response;

import java.util.List;

import com.ssafy.enjoytrip.domain.attraction.AttractionInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionDetail {
    private AttractionInfo attractInfo;
    private AttractDescription attractDescription;
    private List<AttractBoard> attractBoards;
}
