package com.ssafy.enjoytrip.domain.attraction.dto.response;

import com.ssafy.enjoytrip.domain.attraction.AttractionDescription;
import com.ssafy.enjoytrip.domain.attraction.AttractionInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionDetail {
    private AttractionInfo attractionInfo;
    private AttractionDescription attractionDescription;
}
