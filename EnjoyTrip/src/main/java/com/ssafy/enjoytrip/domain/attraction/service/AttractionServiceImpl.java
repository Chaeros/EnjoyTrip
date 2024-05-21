package com.ssafy.enjoytrip.domain.attraction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.domain.attraction.*;
import com.ssafy.enjoytrip.domain.attraction.dto.request.AccomodationRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.request.AttractionRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.request.PlanRequestDto;
import com.ssafy.enjoytrip.domain.attraction.dto.response.AttractionDetail;
import com.ssafy.enjoytrip.domain.attraction.dto.response.AttractionInfoLike;
import com.ssafy.enjoytrip.domain.attraction.mapper.AttractionMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;

    @Override
    public List<AttractionInfoLike> getListAttractionInfoLikesByCond(AttractionRequestDto attractionRequestDto) {
        List<AttractionInfoLike> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        int page = attractionRequestDto.getPage();
        int size = attractionRequestDto.getSize();
        int offset = (page - 1)* size;
        map.put("attractionRequestDto",attractionRequestDto);
        map.put("offset", offset);
        map.put("size", size);
        List<AttractionInfo> AttractionInfos = attractionMapper.selectAttractionInfosByCond(map);
        for (AttractionInfo AttractionInfo : AttractionInfos) {
            int contentId = AttractionInfo.getContentId();
            int likeCnt = attractionMapper.selectAttrLikeCntByContentId(contentId);

            AttractionInfoLike AttractionInfoLike = new AttractionInfoLike();
            AttractionInfoLike.setAttractionInfo(AttractionInfo);
            AttractionInfoLike.setLikeCnt(likeCnt);
            list.add(AttractionInfoLike);
        }

        return list;
    }

    @Override
    public List<AttractionInfoLike> getListAccomInfoLikesByCond(AccomodationRequestDto accomodationRequestDto) {
        List<AttractionInfoLike> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        int page = accomodationRequestDto.getPage();
        int size = accomodationRequestDto.getSize();
        int offset = (page - 1)* size;
        map.put("accomodationRequestDto",accomodationRequestDto);
        map.put("offset", offset);
        map.put("size", size);

        List<AttractionInfo> AttractionInfos = attractionMapper.selectAccomInfosByCond(map);
        for (AttractionInfo AttractionInfo : AttractionInfos) {
            int contentId = AttractionInfo.getContentId();
            int likeCnt = attractionMapper.selectAttrLikeCntByContentId(contentId);

            AttractionInfoLike AttractionInfoLike = new AttractionInfoLike();
            AttractionInfoLike.setAttractionInfo(AttractionInfo);
            AttractionInfoLike.setLikeCnt(likeCnt);
            list.add(AttractionInfoLike);
        }

        return list;
    }

    @Override
    public AttractionInfo getDetailAttractionInfo(int contentId) {
        AttractionInfo attractionInfo = attractionMapper.selectAttractionInfoByContentId(contentId);
        return attractionInfo;
    }

    @Override
    public int getAttractionLikeCnt(int contentId) {
        return attractionMapper.selectAttrLikeCntByContentId(contentId);
    }

    @Override
    public AttractionDetail getAttractionDetailByContentId(int contentId) {
        AttractionInfo AttractionInfo = attractionMapper.selectAttractionInfoByContentId(contentId);
        AttractionDescription AttractionDescription = attractionMapper.selectAttractionDescriptionByContentId(contentId);

        AttractionDetail AttractionDetail = new AttractionDetail();
        AttractionDetail.setAttractionInfo(AttractionInfo);
        AttractionDetail.setAttractionDescription(AttractionDescription);

        return AttractionDetail;
    }

    @Override
    public List<AttractionInfoLike> getListAttractionInfoLikesByMemberId(int memberId) {
        List<AttractionInfoLike> list = new ArrayList<>();

        List<AttractionInfo> AttractionInfos = attractionMapper.selectAttractionInfosByMemberId(memberId);
        for (AttractionInfo AttractionInfo : AttractionInfos) {
            int contentId = AttractionInfo.getContentId();
            int likeCnt = attractionMapper.selectAttrLikeCntByContentId(contentId);

            AttractionInfoLike AttractionInfoLike = new AttractionInfoLike();
            AttractionInfoLike.setAttractionInfo(AttractionInfo);
            AttractionInfoLike.setLikeCnt(likeCnt);
            list.add(AttractionInfoLike);
        }

        return list;
    }









    @Override
    public List<Sido> getListSidos() {
        return attractionMapper.selectSidos();
    }

    @Override
    public List<Gugun> getListGugunsBySidoCode(int sidoCode) {
        return attractionMapper.selectGugunsBySidoCode(sidoCode);
    }

    @Override
    public List<ContentType> getListContentTypeIds() {
        return attractionMapper.selectContentTypes();
    }







}
