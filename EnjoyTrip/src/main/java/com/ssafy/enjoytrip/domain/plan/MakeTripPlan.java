package com.ssafy.enjoytrip.domain.plan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MakeTripPlan {
    private int id;
    private int sequence;
    private String departureTime;
    private String arrivalTime;
    private String memo;
    private String moveTime;
    private String tripDate;
    private String memberId;
    private int tripPlanId;
    private int attractionId;
}
