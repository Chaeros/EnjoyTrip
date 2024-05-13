package com.ssafy.enjoytrip.domain.attraction.dto.response;

import java.util.List;

import com.ssafy.enjoytrip.domain.attraction.AttractionBoard;
import com.ssafy.enjoytrip.domain.attraction.AttractionDescription;
import com.ssafy.enjoytrip.domain.attraction.AttractionInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionDetail {
    private AttractionInfo AttractionInfo;
    private AttractionDescription AttractionDescription;
    private List<AttractionBoard> AttractionBoards;
}
