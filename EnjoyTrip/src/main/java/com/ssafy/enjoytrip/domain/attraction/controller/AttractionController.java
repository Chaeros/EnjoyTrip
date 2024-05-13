package com.ssafy.enjoytrip.domain.attraction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.attraction.model.AccomRequest;
import com.ssafy.attraction.model.AttractDetail;
import com.ssafy.attraction.model.AttractInfoLike;
import com.ssafy.attraction.model.AttractRequest;
import com.ssafy.attraction.model.ContentType;
import com.ssafy.attraction.model.Gugun;
import com.ssafy.attraction.model.PlanRequest;
import com.ssafy.attraction.model.Sido;
import com.ssafy.attraction.model.service.AttractService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attraction")
public class AttractController {

    private final AttractService attractService;

    @GetMapping("/attraction")
    public ResponseEntity<?> getListAttractInfoLikesByCond(AttractRequest attractRequest) {
        List<AttractInfoLike> list = attractService.getListAttractInfoLikesByCond(attractRequest);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractInfoLike>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/accomodation")
    public ResponseEntity<?> getListAccomInfoLikesByCond(AccomRequest accomRequest) {
        List<AttractInfoLike> list = attractService.getListAccomInfoLikesByCond(accomRequest);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractInfoLike>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<?> attractDetailByContentId(@PathVariable("contentId") int contentId) {
        AttractDetail attractDetail = attractService.getAttractDetailByContentId(contentId);

        if (attractDetail != null) {
            return new ResponseEntity<AttractDetail>(attractDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/like/{memberId}")
    public ResponseEntity<?> likeAttractDetailByContentId(@PathVariable("memberId") int memberId) {
        List<AttractInfoLike> list = attractService.getListAttractInfoLikesByMemberId(memberId);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractInfoLike>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<?> planAttractDetailByPlanId(@PathVariable("planId") int planId) {
        List<AttractInfoLike> list = attractService.getListAttractInfoLikesByPlanId(planId);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractInfoLike>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/plan")
    public ResponseEntity<?> planAttractDetailByPlanIdAndDetail(PlanRequest planRequest) {
        List<AttractInfoLike> list = attractService.getListAttractInfoLikesByPlanIdAndDay(planRequest);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractInfoLike>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/sido")
    public ResponseEntity<?> listSidos() {
        List<Sido> list = attractService.getListSidos();

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<Sido>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/sido/{sido}")
    public ResponseEntity<?> listGugunsBySidoCode(@PathVariable("sido") int sidoCode) {
        List<Gugun> list = attractService.getListGugunsBySidoCode(sidoCode);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<Gugun>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/contenttype")
    public ResponseEntity<?> listContentTypeIds() {
        List<ContentType> list = attractService.getListContentTypeIds();

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<ContentType>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

}
