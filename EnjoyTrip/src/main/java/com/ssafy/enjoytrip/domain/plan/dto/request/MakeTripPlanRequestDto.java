package com.ssafy.enjoytrip.domain.plan.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MakeTripPlanRequestDto {
    private int tripPlanId;
    private int tripDate;
    private int sequence;
}
