package com.ssafy.enjoytrip.domain.attractionboardcomment.service;

import com.ssafy.enjoytrip.domain.attractionboard.AttractionBoard;
import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.AttractionBoardComment;
import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.request.AttractionBoardCommentAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.request.AttractionBoardCommentModifyRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.response.AttractionBoardCommentResponseDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.mapper.AttractionBoardCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionBoardCommentServiceImpl implements AttractionBoardCommentService{

    private final AttractionBoardCommentMapper attractionBoardCommentMapper;

    @Override
    public int addAttractionBoardComment(AttractionBoardCommentAddRequestDto attractionBoardCommentAddRequestDto) {
        return attractionBoardCommentMapper.insertAttractionBoardComment(attractionBoardCommentAddRequestDto.transferToAttractionBoardComment());
    }

    @Override
    public int modifyAttractionBoardComment(AttractionBoardCommentModifyRequestDto attractionBoardCommentModifyRequestDto) {
        return attractionBoardCommentMapper.updateAttractionBoardComment(attractionBoardCommentModifyRequestDto.transferToAttractionBoardComment());
    }

    @Override
    public int removeAttractionBoardComment(int id) {
        return attractionBoardCommentMapper.deleteAttractionBoardComment(id);
    }

    @Override
    public Optional<List<AttractionBoardCommentResponseDto>> searchAttractionBoardComments(int attractionBoardId) {
        return Optional.ofNullable(
                attractionBoardCommentMapper
                        .findAttractionBoardComments(attractionBoardId)
                        .stream()
                        .map(AttractionBoardComment::transferToAttractionBoardCommentResponseDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public int searchAttractionBoardCommentCount(int attractionBoardId) {
        return attractionBoardCommentMapper.findCommentCount(attractionBoardId);
    }
}
