package com.ssafy.enjoytrip.domain.attractionboard.mapper;

import com.ssafy.enjoytrip.domain.attractionboard.AttractionBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionBoardMapper {
    int insertAttractionBoard(AttractionBoard attractionBoard);
    void updateAttractionBoard(AttractionBoard attractionBoard);
    void deleteAttractionBoard(int id);
    AttractionBoard findAttractionBoaard(int id);
    List<AttractionBoard> findAttractionBoards();
    List<AttractionBoard> findAttractinoBoardsByTitle(String title);
    List<AttractionBoard> findAttractionBoardsByAttractionId(int attractionId);
}
