package com.ssafy.enjoytrip.domain.attractionboardimage.service;

import com.ssafy.enjoytrip.domain.attractionboardimage.AttractionBoardImage;
import com.ssafy.enjoytrip.domain.attractionboardimage.dto.request.AttractionBoardImageAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardimage.mapper.AttractionBoardImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttractionBoardImageServiceImpl implements AttractionBoardImageService {
    private final AttractionBoardImageMapper attractionBoardImageMapper;


    @Override
    public int addAttractionBoardImage(AttractionBoardImageAddRequestDto attractionBoardImageAddRequestDto) {
        return attractionBoardImageMapper.insertAttractionBoardImage(attractionBoardImage);
    }

    @Override
    public Optional<AttractionBoardImage> searchAttractionBoardImage(int attractionBoardId) {
        return Optional.ofNullable(attractionBoardImageMapper.findAttractionBoardImage(attractionBoardId));
    }
}
