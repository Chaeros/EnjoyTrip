package com.ssafy.enjoytrip.domain.attraction.controller;

import java.util.List;

import com.ssafy.enjoytrip.domain.attraction.ContentType;
import com.ssafy.enjoytrip.domain.attraction.Gugun;
import com.ssafy.enjoytrip.domain.attraction.Sido;
import com.ssafy.enjoytrip.domain.attraction.dto.request.AccomodationRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.request.AttractionRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.request.PlanRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.response.AttractionDetail;
import com.ssafy.enjoytrip.domain.attraction.dto.response.AttractionInfoLike;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attraction")
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping("/attraction")
    public ResponseEntity<?> getListAttractionInfoLikesByCond(AttractionRequestDto AttractionRequestDto) {
        List<AttractionInfoLike> list = attractionService.getListAttractionInfoLikesByCond(AttractionRequestDto);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractionInfoLike>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/accomodation")
    public ResponseEntity<?> getListAccomInfoLikesByCond(AccomodationRequestDto AccomodationRequestDto) {
        List<AttractionInfoLike> list = attractionService.getListAccomInfoLikesByCond(AccomodationRequestDto);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractionInfoLike>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<?> AttractionDetailByContentId(@PathVariable("contentId") int contentId) {
        AttractionDetail attractionDetail = attractionService.getAttractionDetailByContentId(contentId);

        if (attractionDetail != null) {
            return new ResponseEntity<AttractionDetail>(attractionDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/like/{memberId}")
    public ResponseEntity<?> likeAttractionDetailByContentId(@PathVariable("memberId") int memberId) {
        List<AttractionInfoLike> list = attractionService.getListAttractionInfoLikesByMemberId(memberId);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractionInfoLike>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/sido")
    public ResponseEntity<?> listSidos() {
        System.out.println("sido");
        List<Sido> list = attractionService.getListSidos();

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<Sido>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/sido/{sido}")
    public ResponseEntity<?> listGugunsBySidoCode(@PathVariable("sido") int sidoCode) {
        List<Gugun> list = attractionService.getListGugunsBySidoCode(sidoCode);

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<Gugun>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/contenttype")
    public ResponseEntity<?> listContentTypeIds() {
        List<ContentType> list = attractionService.getListContentTypeIds();

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<ContentType>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

}
