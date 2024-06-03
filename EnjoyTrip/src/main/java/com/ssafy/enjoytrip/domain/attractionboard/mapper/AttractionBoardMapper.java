package com.ssafy.enjoytrip.domain.attractionboard.mapper;

import com.ssafy.enjoytrip.domain.attractionboard.AttractionBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionBoardMapper {
    int insertAttractionBoard(AttractionBoard attractionBoard);
    int updateAttractionBoard(AttractionBoard attractionBoard);
    int deleteAttractionBoard(int id);
    int findTotalCountOfAttractionBoard();
    int updateHitCount(int id);
    AttractionBoard findAttractionBoard(int id);
    List<AttractionBoard> findAttractionBoards();
    List<AttractionBoard> findAttractionBoardsByTitle(String title);
    List<AttractionBoard> findAttractionBoardsByAttractionId(int attractionId);
    List<AttractionBoard> findAttractionBoardsSortByLike();
    List<AttractionBoard> findPageOfAttractionBoard(int offset);
}
