package com.ssafy.enjoytrip.domain.plan.service;

import com.ssafy.enjoytrip.domain.plan.dto.request.TripPlanRequest;

import java.util.List;

public interface PlanService {
    int registTripPlan(TripPlanRequest tripPlanRequest);
//	int registMakeTripPlan(MakeTripPlan makeTripPlan);

    int editTripPlan(TripPlanRequest tripPlanRequest);
    int removeTripPlan(int id);

    List<TripPlanRequest> getListMyTripPlan(int memberId);
    TripPlanRequest getDetailTripPlan(int planId);
}

