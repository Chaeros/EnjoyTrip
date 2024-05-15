package com.ssafy.enjoytrip.domain.plan.dto.request;

import java.util.List;

import com.ssafy.enjoytrip.domain.plan.MakeTripPlan;
import com.ssafy.enjoytrip.domain.plan.TripPlan;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripPlanRequest {
    private TripPlan tripPlan;
    private List<MakeTripPlan> makeTripPlans;
}