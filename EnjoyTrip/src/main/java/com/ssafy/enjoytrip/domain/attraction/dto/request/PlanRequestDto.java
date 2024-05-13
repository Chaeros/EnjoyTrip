package com.ssafy.enjoytrip.domain.attraction.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlanRequestDto {
    private int planId;
    private String tripDate;
}
