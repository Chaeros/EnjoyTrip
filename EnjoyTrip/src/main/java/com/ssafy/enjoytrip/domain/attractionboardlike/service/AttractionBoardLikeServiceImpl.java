package com.ssafy.enjoytrip.domain.attractionboardlike.service;

import com.ssafy.enjoytrip.domain.attractionboardlike.AttractionBoardLike;
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
    public int removeAttractionBoardLike(AttractionBoardLike attractionBoardLike) {
        return attractionBoardLikeMapper.deleteAttractionBoardLike(attractionBoardLike);
    }

    @Override
    public int searchAttractionBoardLike(AttractionBoardLike attractionBoardLike) {
        return attractionBoardLikeMapper.findAttractionBoardLike(attractionBoardLike);
    }

    @Override
    public int searchAttractionBoardLikeCount(int attractionBoardId) {
        return attractionBoardLikeMapper.findAttractionBoardLikeCount(attractionBoardId);
    }
}
