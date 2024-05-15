package com.ssafy.enjoytrip.domain.plan.mapper;

import com.ssafy.enjoytrip.domain.plan.MakeTripPlan;
import com.ssafy.enjoytrip.domain.plan.TripPlan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {

    int insertTripPlan(TripPlan tripPlan);
    int insertMakeTripPlan(MakeTripPlan makeTripPlan);
    int selectTripPlanMaxId();
    int updateTripPlan(TripPlan tripPlan);
    int deleteMakeTripPlan(int tripPlanId);
    int deleteTripPlan(int id);
    List<Integer> selectMyTripPlanIds(int memberId);
    TripPlan selectTripPlanById(int id);
    List<MakeTripPlan> selectMakeTripPlansByTripPlanId(int tripPlanId);
}

