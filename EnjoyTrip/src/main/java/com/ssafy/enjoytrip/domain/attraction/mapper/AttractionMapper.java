package com.ssafy.enjoytrip.domain.attraction.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.domain.attraction.*;
import com.ssafy.enjoytrip.domain.attraction.dto.request.AccomodationRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.request.AttractionRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.request.PlanRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionMapper {


    // 관광지 조건부 조회
    List<AttractionInfo> selectAttractionInfosByCond(Map<String,Object> map);

    // 숙소 조건부 조회
    List<AttractionInfo> selectAccomInfosByCond(Map<String,Object> map);

    // 관광지별 좋아요 개수 불러오기
    int selectAttrLikeCntByContentId(int contentId);



    // 관광지별 관광지 정보 조회
    AttractionInfo selectAttractionInfoByContentId(int contentId);

    // 관광지별 상세정보(description) 가져오기
    AttractionDescription selectAttractionDescriptionByContentId(int contentId);

    // 내가 좋아요 누른 관광지 목록 조회
    List<AttractionInfo> selectAttractionInfosByMemberId(int memberId);



    // 모든 시도 불러오기
    List<Sido> selectSidos();

    // 시도에 따른 구군 불러오기
    List<Gugun> selectGugunsBySidoCode(int sidoCode);

    // 모든 컨텐츠타입(식당,카페,...) 불러오기
    List<ContentType> selectContentTypes();


}

