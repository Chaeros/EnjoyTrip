package com.ssafy.enjoytrip.domain.attraction.service;

import java.util.ArrayList;
import java.util.List;

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
    public List<AttractionInfoLike> getListAttractionInfoLikesByCond(AttractionRequestDto AttractionRequestDto) {
        List<AttractionInfoLike> list = new ArrayList<>();

        List<AttractionInfo> AttractionInfos = attractionMapper.selectAttractionInfosByCond(AttractionRequestDto);
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
    public List<AttractionInfoLike> getListAccomInfoLikesByCond(AccomodationRequestDto AccomodationRequestDto) {
        List<AttractionInfoLike> list = new ArrayList<>();

        List<AttractionInfo> AttractionInfos = attractionMapper.selectAccomInfosByCond(AccomodationRequestDto);
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
    public AttractionDetail getAttractionDetailByContentId(int contentId) {
        AttractionInfo AttractionInfo = attractionMapper.selectAttractionInfoByContentId(contentId);
        AttractionDescription AttractionDescription = attractionMapper.selectAttractionDescriptionByContentId(contentId);
        List<AttractionBoard> AttractionBoards = attractionMapper.selectAttractionBoardsByContentId(contentId);

        AttractionDetail AttractionDetail = new AttractionDetail();
        AttractionDetail.setAttractionInfo(AttractionInfo);
        AttractionDetail.setAttractionDescription(AttractionDescription);
        AttractionDetail.setAttractionBoards(AttractionBoards);

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
    public List<AttractionInfoLike> getListAttractionInfoLikesByPlanId(int planId) {
        List<AttractionInfoLike> list = new ArrayList<>();

        List<AttractionInfo> AttractionInfos = attractionMapper.selectAttractionInfosByPlanId(planId);
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
    public List<AttractionInfoLike> getListAttractionInfoLikesByPlanIdAndDay(PlanRequestDto PlanRequestDto) {
        List<AttractionInfoLike> list = new ArrayList<>();

        List<AttractionInfo> AttractionInfos = attractionMapper.selectAttractionInfosByPlanIdAndDay(PlanRequestDto);
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
