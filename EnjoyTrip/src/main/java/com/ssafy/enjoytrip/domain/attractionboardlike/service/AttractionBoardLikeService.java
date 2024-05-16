package com.ssafy.enjoytrip.domain.attractionboardlike.service;

import com.ssafy.enjoytrip.domain.attractionboardlike.dto.AttractionBoardLikeAddRequestDto;

public interface AttractionBoardLikeService {
    int addAttractionBoardLike(AttractionBoardLikeAddRequestDto attractionBoardLikeAddRequestDto);
    int removeAttractionBoardLike(long memberId);
    int searchAttractionBoardLike(long memberId);
    int searchAttractionBoardLikeCount(int attractionBoardId);
}
