package com.ssafy.enjoytrip.domain.attractionboardcomment.controller;

import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.request.AttractionBoardCommentAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.request.AttractionBoardCommentModifyRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.dto.response.AttractionBoardCommentResponseDto;
import com.ssafy.enjoytrip.domain.attractionboardcomment.service.AttractionBoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attractionboardcommnet")
public class AttractionBoardCommentController {

    private final AttractionBoardCommentService attractionBoardCommentService;

    @PostMapping
    public ResponseEntity<Void> addAttractionBoardComment(@RequestBody AttractionBoardCommentAddRequestDto attractionBoardCommentAddRequestDto){
        if ( attractionBoardCommentService.addAttractionBoardComment(attractionBoardCommentAddRequestDto) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> modifyAttractionBoardComment(@RequestBody AttractionBoardCommentModifyRequestDto attractionBoardCommentModifyRequestDto){
        if ( attractionBoardCommentService.modifyAttractionBoardComment(attractionBoardCommentModifyRequestDto) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{attractionCommentId}")
    public ResponseEntity<Void> removeAttractionBoardComment(@PathVariable("attractionCommentId") int attractionCommentId){
        if ( attractionBoardCommentService.removeAttractionBoardComment(attractionCommentId) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{attractionBoardId}")
    public ResponseEntity<List<AttractionBoardCommentResponseDto>> searchAttractionBoardComment(@PathVariable("attractionBoardId") int attractionBoardId){
        return attractionBoardCommentService.searchAttractionBoardComments(attractionBoardId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}