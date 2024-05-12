package com.ssafy.enjoytrip.domain.attractionboard.service;

import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardModifyDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;

import java.util.List;
import java.util.Optional;

public interface AttractionBoardService {
    int addAttractionBoard(AttractionBoardAddRequestDto attractionBoardAddRequestDto);
    int modifyAttractionBoard(AttractionBoardModifyDto attractionBoardModifyDto);
    int removeAttractionBoard(int id);
    Optional<AttractionBoardResponseDto> searchAttractionBoard(int id);
    Optional<List<AttractionBoardResponseDto>> searchAttractionBoards();
    Optional<List<AttractionBoardResponseDto>> searchAttractionBoardsByTitle(String title);
    Optional<List<AttractionBoardResponseDto>> searchAttractionBoardsByAttractionId(int attractionId);
}
