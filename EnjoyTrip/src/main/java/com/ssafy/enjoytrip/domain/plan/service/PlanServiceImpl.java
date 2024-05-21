package com.ssafy.enjoytrip.domain.plan.service;

import com.ssafy.enjoytrip.domain.plan.MakeTripPlan;
import com.ssafy.enjoytrip.domain.plan.TripPlan;
import com.ssafy.enjoytrip.domain.plan.dto.request.MakeTripPlanRequestDto;
import com.ssafy.enjoytrip.domain.plan.dto.request.TripPlanRequest;
import com.ssafy.enjoytrip.domain.plan.mapper.PlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {

    private final PlanMapper planMapper;

    @Override
    public int registTripPlan(TripPlanRequest tripPlanRequest) {
        if(planMapper.insertTripPlan(tripPlanRequest.getTripPlan()) == 0) return 0;
        int maxTripPlanId = planMapper.selectTripPlanMaxId();
        for (MakeTripPlan makeTripPlan : tripPlanRequest.getMakeTripPlans()) {
            makeTripPlan.setTripPlanId(maxTripPlanId);
            if(planMapper.insertMakeTripPlan(makeTripPlan) == 0) return 0;
        }

        return maxTripPlanId;
    }

    @Override
    public int editTripPlan(TripPlanRequest tripPlanRequest) {
        if (planMapper.deleteMakeTripPlan(tripPlanRequest.getTripPlan().getId()) == 0) return 0;
        for (MakeTripPlan makeTripPlan : tripPlanRequest.getMakeTripPlans()) {
            if (planMapper.insertMakeTripPlan(makeTripPlan) == 0) return 0;
        }
        planMapper.updateTripPlan(tripPlanRequest.getTripPlan());

        return 1;
    }

    @Override
    public int removeTripPlan(int id) {
        return planMapper.deleteTripPlan(id);
    }


    @Override
    public List<TripPlanRequest> getListMyTripPlan(int memberId) {
        List<Integer> myTripPlanIds = planMapper.selectMyTripPlanIds(memberId);
        List<TripPlanRequest> list = new ArrayList<>();

        for (int myTripPlanId : myTripPlanIds) {
            TripPlanRequest tripPlanRequest = new TripPlanRequest();
            TripPlan tripPlan = planMapper.selectTripPlanById(myTripPlanId);
            List<MakeTripPlan> makeTripPlans = planMapper.selectMakeTripPlansByTripPlanId(myTripPlanId);
            tripPlanRequest.setTripPlan(tripPlan);
            tripPlanRequest.setMakeTripPlans(makeTripPlans);
            list.add(tripPlanRequest);
        }

        return list;
    }

    @Override
    public TripPlanRequest getDetailTripPlan(int planId) {
        TripPlanRequest tripPlanRequest = new TripPlanRequest();
        TripPlan tripPlan = planMapper.selectTripPlanById(planId);
        List<MakeTripPlan> makeTripPlans = planMapper.selectMakeTripPlansByTripPlanId(planId);
        tripPlanRequest.setTripPlan(tripPlan);
        tripPlanRequest.setMakeTripPlans(makeTripPlans);

        return tripPlanRequest;
    }

    @Override
    public MakeTripPlan getDetailMakeTripPlan(MakeTripPlanRequestDto makeTripPlanRequestDto) {
        return planMapper.selectMakeTripPlanByIdDateSequence(makeTripPlanRequestDto);
    }
}
