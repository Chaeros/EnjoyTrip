package com.ssafy.enjoytrip.domain.attractionboardcomment.service;

import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.request.AttractionBoardCommentAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.request.AttractionBoardCommentModifyRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.response.AttractionBoardCommentResponseDto;

import java.util.List;
import java.util.Optional;

public interface AttractionBoardCommentService {
    int addAttractionBoardComment(AttractionBoardCommentAddRequestDto attractionBoardCommentAddRequestDto);
    int modifyAttractionBoardComment(AttractionBoardCommentModifyRequestDto attractionBoardCommentModifyRequestDto);
    int removeAttractionBoardComment(int id);
    Optional<List<AttractionBoardCommentResponseDto>> searchAttractionBoardComments(int attractionBoardId);
//    Optional<List<AttractionBoardCommentResponseDto>> searchAttractionBoardCommentsByMemberId();
}
