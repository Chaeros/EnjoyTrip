package com.ssafy.enjoytrip.domain.attractionboardlike.service;

import com.ssafy.enjoytrip.domain.attractionboardlike.AttractionBoardLike;
import com.ssafy.enjoytrip.domain.attractionboardlike.dto.AttractionBoardLikeAddRequestDto;

public interface AttractionBoardLikeService {
    int addAttractionBoardLike(AttractionBoardLikeAddRequestDto attractionBoardLikeAddRequestDto);
    int removeAttractionBoardLike(AttractionBoardLike attractionBoardLike);
    int searchAttractionBoardLike(AttractionBoardLike attractionBoardLike);
    int searchAttractionBoardLikeCount(int attractionBoardId);
}
