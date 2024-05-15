package com.ssafy.enjoytrip.domain.attractionboardimage.service;

import com.ssafy.enjoytrip.domain.attractionboardimage.AttractionBoardImage;
import com.ssafy.enjoytrip.domain.attractionboardimage.dto.request.AttractionBoardImageAddRequestDto;

import java.util.Optional;

public interface AttractionBoardImageService {
    int addAttractionBoardImage(AttractionBoardImageAddRequestDto attractionBoardImageAddRequestDto);
    Optional<AttractionBoardImage> searchAttractionBoardImage(int attractionBoardId);
}
