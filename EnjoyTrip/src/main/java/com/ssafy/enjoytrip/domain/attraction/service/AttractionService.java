package com.ssafy.enjoytrip.domain.attraction.service;

import java.util.List;

import com.ssafy.enjoytrip.domain.attraction.ContentType;
import com.ssafy.enjoytrip.domain.attraction.Gugun;
import com.ssafy.enjoytrip.domain.attraction.Sido;
import com.ssafy.enjoytrip.domain.attraction.dto.request.AccomodationRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.request.AttractionRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.request.PlanRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.response.AttractionDetail;
import com.ssafy.enjoytrip.domain.attraction.dto.response.AttractionInfoLike;

public interface AttractionService {
    List<AttractionInfoLike> getListAttractionInfoLikesByCond(AttractionRequestDto AttractionRequestDto);
    List<AttractionInfoLike> getListAccomInfoLikesByCond(AccomodationRequestDto AccomodationRequestDto);

    AttractionDetail getAttractionDetailByContentId(int contentId);
    List<AttractionInfoLike> getListAttractionInfoLikesByMemberId(int memberId);

    List<AttractionInfoLike> getListAttractionInfoLikesByPlanId(int memberId);
    List<AttractionInfoLike> getListAttractionInfoLikesByPlanIdAndDay(PlanRequestDto PlanRequestDto);


    List<Sido> getListSidos();
    List<Gugun> getListGugunsBySidoCode(int sidoCode);
    List<ContentType> getListContentTypeIds();
}
