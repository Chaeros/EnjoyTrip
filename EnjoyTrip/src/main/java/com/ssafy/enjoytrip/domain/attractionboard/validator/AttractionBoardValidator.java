package com.ssafy.enjoytrip.domain.attractionboard.validator;

import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboard.exception.AttractionBoardException;
import org.springframework.stereotype.Component;

@Component
public class AttractionBoardValidator {

    public void validateIsNotNullTitle(String title){
        if ( title == null ){
            throw new AttractionBoardException("제목을 입력해주세요!");
        }
    }

    public void validateIsNotNullContent(String content){
        if ( content == null ){
            throw new AttractionBoardException("내용을 입력해주세요!");
        }
    }

    public void validateAttractionBoardAddRequestDto(AttractionBoardAddRequestDto attractionBoardAddRequestDto){
        validateIsNotNullTitle(attractionBoardAddRequestDto.getTitle());
        validateIsNotNullContent(attractionBoardAddRequestDto.getContent());
    }

}
