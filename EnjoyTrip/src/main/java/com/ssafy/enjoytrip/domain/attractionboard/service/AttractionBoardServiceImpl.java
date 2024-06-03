package com.ssafy.enjoytrip.domain.attractionboard.service;

import com.ssafy.enjoytrip.domain.attractionboard.AttractionBoard;
import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardModifyRequestDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;
import com.ssafy.enjoytrip.domain.attractionboard.mapper.AttractionBoardMapper;
import com.ssafy.enjoytrip.domain.attractionboard.validator.AttractionBoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttractionBoardServiceImpl implements AttractionBoardService{
    
    private final AttractionBoardMapper attractionBoardMapper;
    private final AttractionBoardValidator attractionBoardValidator;
    
    @Override
    public int addAttractionBoard(AttractionBoardAddRequestDto attractionBoardAddRequestDto) {
        attractionBoardValidator.validateAttractionBoardAddRequestDto(attractionBoardAddRequestDto);
        return attractionBoardMapper.insertAttractionBoard(attractionBoardAddRequestDto.transferToAttractionBoard());
    }

    @Override
    public int modifyAttractionBoard(AttractionBoardAddRequestDto attractionBoardAddRequestDto) {
        attractionBoardValidator.validateAttractionBoardAddRequestDto(attractionBoardAddRequestDto);
        return attractionBoardMapper.updateAttractionBoard(attractionBoardAddRequestDto.transferToAttractionBoard());
    }

    @Override
    public int removeAttractionBoard(int id) {
        return attractionBoardMapper.deleteAttractionBoard(id);
    }

    @Override
    public int searchTotalCountOfAttractionBoard() {
        return attractionBoardMapper.findTotalCountOfAttractionBoard();
    }

    @Override
    public int addHitCount(int id){
        return attractionBoardMapper.updateHitCount(id);
    }

    @Override
    public Optional<AttractionBoardResponseDto> searchAttractionBoard(int id) {
        return Optional.ofNullable(attractionBoardMapper.findAttractionBoard(id).transferToAttractionBoardResponseDto());
    }

    @Override
    public Optional<List<AttractionBoardResponseDto>> searchAttractionBoards() {
        List<AttractionBoard> boards = attractionBoardMapper.findAttractionBoards();
        List<AttractionBoardResponseDto> responseDtos = boards.stream()
                .map(AttractionBoard::transferToAttractionBoardResponseDto)
                .collect(Collectors.toList());
        return Optional.ofNullable(responseDtos);
    }

    @Override
    public Optional<List<AttractionBoardResponseDto>> searchAttractionBoardsByTitle(String title) {
        List<AttractionBoard> boards = attractionBoardMapper.findAttractionBoardsByTitle(title);
        List<AttractionBoardResponseDto> responseDtos = boards.stream()
                .map(AttractionBoard::transferToAttractionBoardResponseDto)
                .collect(Collectors.toList());
        return Optional.ofNullable(responseDtos);
    }

    @Override
    public Optional<List<AttractionBoardResponseDto>> searchAttractionBoardsByAttractionId(int attractionId) {
        List<AttractionBoard> boards = attractionBoardMapper.findAttractionBoardsByAttractionId(attractionId);
        List<AttractionBoardResponseDto> responseDtos = boards.stream()
                .map(AttractionBoard::transferToAttractionBoardResponseDto)
                .collect(Collectors.toList());
        return Optional.ofNullable(responseDtos);
    }

    @Override
    public Optional<List<AttractionBoardResponseDto>> searchPageOfAttractionBoard(int page) {
        List<AttractionBoard> boards = attractionBoardMapper.findPageOfAttractionBoard(page*6);
        List<AttractionBoardResponseDto> responseDtos = boards.stream()
                .map(AttractionBoard::transferToAttractionBoardResponseDto)
                .collect(Collectors.toList());
        return Optional.ofNullable(responseDtos);
    }
}
