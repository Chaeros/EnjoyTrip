package com.ssafy.enjoytrip.domain.attractionboardcomment.mapper;

import com.ssafy.enjoytrip.domain.attractionboardcomment.AttractionBoardComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionBoardCommentMapper {
    int insertAttractionBoardComment(AttractionBoardComment attractionBoardComment);
    int updateAttractionBoardComment(AttractionBoardComment attractionBoardComment);
    int deleteAttractionBoardComment(int id);
    List<AttractionBoardComment> findAttractionBoardComments(int attractionBoardId);
    int findCommentCount(int attractionBoardId);
}
