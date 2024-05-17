package com.ssafy.enjoytrip.domain.attractionboardlike.mapper;

import com.ssafy.enjoytrip.domain.attractionboardlike.AttractionBoardLike;
import com.ssafy.enjoytrip.domain.attractionboardlike.dto.AttractionBoardLikeAddRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionBoardLikeMapper {
    int insertAttractionBoardLike(AttractionBoardLikeAddRequestDto attractionBoardLikeAddRequestDto);
    int deleteAttractionBoardLike(AttractionBoardLike attractionBoardLike);
    int findAttractionBoardLike(AttractionBoardLike attractionBoardLike);
    int findAttractionBoardLikeCount(int attractionBoardId);
}
