package com.ssafy.enjoytrip.domain.attractionboardimage.mapper;

import com.ssafy.enjoytrip.domain.attractionboardimage.AttractionBoardImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionBoardImageMapper {
    int insertAttractionBoardImage(AttractionBoardImage attractionBoardImage);
    int deleteAttractionBoardImages(int attractionBoardId);
    AttractionBoardImage findAttractionBoardImage(int attractionBoardId);
}
