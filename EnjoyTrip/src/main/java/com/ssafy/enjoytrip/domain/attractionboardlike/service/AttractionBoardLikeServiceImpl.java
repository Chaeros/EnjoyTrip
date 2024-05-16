package com.ssafy.enjoytrip.domain.attractionboardlike.service;

import com.ssafy.enjoytrip.domain.attractionboardlike.dto.AttractionBoardLikeAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardlike.mapper.AttractionBoardLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionBoardLikeServiceImpl implements AttractionBoardLikeService{

    private final AttractionBoardLikeMapper attractionBoardLikeMapper;

    @Override
    public int addAttractionBoardLike(AttractionBoardLikeAddRequestDto attractionBoardLikeAddRequestDto) {
        return attractionBoardLikeMapper.insertAttractionBoardLike(attractionBoardLikeAddRequestDto);
    }

    @Override
    public int removeAttractionBoardLike(long memberId) {
        return attractionBoardLikeMapper.deleteAttractionBoardLike(memberId);
    }

    @Override
    public int searchAttractionBoardLike(long memberId) {
        return attractionBoardLikeMapper.findAttractionBoardLike(memberId);
    }

    @Override
    public int searchAttractionBoardLikeCount(int attractionBoardId) {
        return attractionBoardLikeMapper.findAttractionBoardLikeCount(attractionBoardId);
    }
}
