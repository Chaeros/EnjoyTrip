package com.ssafy.enjoytrip.domain.attractionboard.service;

import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardModifyRequestDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardLikeResponseDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AttractionBoardService {
    int addAttractionBoard(AttractionBoardAddRequestDto attractionBoardAddRequestDto);
    int modifyAttractionBoard(AttractionBoardAddRequestDto attractionBoardAddRequestDto);
    int removeAttractionBoard(int id);
    int searchTotalCountOfAttractionBoard();
    int addHitCount(int id);
    Optional<AttractionBoardResponseDto> searchAttractionBoard(int id);
    Optional<List<AttractionBoardResponseDto>> searchAttractionBoards();
    Optional<List<AttractionBoardResponseDto>> searchAttractionBoardsByTitle(String title);
    Optional<List<AttractionBoardResponseDto>> searchAttractionBoardsByAttractionId(int attractionId);
    Optional<List<AttractionBoardResponseDto>> searchPageOfAttractionBoard(int page);
    Optional<List<AttractionBoardLikeResponseDto>> searchAttractionBoardsSortByLike(int page);
}
