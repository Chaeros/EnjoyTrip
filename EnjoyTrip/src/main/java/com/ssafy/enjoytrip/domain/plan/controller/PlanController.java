package com.ssafy.enjoytrip.domain.plan.controller;

import com.ssafy.enjoytrip.domain.plan.dto.request.TripPlanRequest;
import com.ssafy.enjoytrip.domain.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<?> registTripPlan(@RequestBody TripPlanRequest tripPlanRequest) {
        int insertResult = planService.registTripPlan(tripPlanRequest);

        if (insertResult > 0) {
            return new ResponseEntity<Integer>(insertResult, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("trip plan 생성 실패");
        }
    }

    @PutMapping
    public ResponseEntity<?> editTripPlan(@RequestBody TripPlanRequest tripPlanRequest) {
        int editResult = planService.editTripPlan(tripPlanRequest);

        if (editResult > 0) {
            return new ResponseEntity<Integer>(editResult, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("trip plan 수정 실패");
        }
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<?> removeTripPlan(@PathVariable("planId") int planId) {
        int removeResult = planService.removeTripPlan(planId);

        if (removeResult > 0) {
            return new ResponseEntity<Integer>(removeResult, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("trip plan 삭제 실패");
        }
    }

    @GetMapping("/myplan/{memberId}")
    public ResponseEntity<?> getListMyTripPlan(@PathVariable("memberId") int memberId) {
        List<TripPlanRequest> list = planService.getListMyTripPlan(memberId);
        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<TripPlanRequest>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/planid/{planId}")
    public ResponseEntity<?> getDetailTripPlan(@PathVariable("planId") int planId) {
        TripPlanRequest tripPlanRequest = planService.getDetailTripPlan(planId);
        if (tripPlanRequest != null) {
            return new ResponseEntity<TripPlanRequest>(tripPlanRequest, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

}

