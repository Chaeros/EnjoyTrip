package com.ssafy.enjoytrip.domain.attractionboardlike.controller;

import com.ssafy.enjoytrip.domain.attractionboardlike.dto.AttractionBoardLikeAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardlike.service.AttractionBoardLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/attractionboardlike")
public class AttractionBoardLikeController {

    private final AttractionBoardLikeService attractionBoardLikeService;

    @PostMapping
    public ResponseEntity<Void> clickAttractionBoardLike(@RequestBody AttractionBoardLikeAddRequestDto attractionBoardLikeAddRequestDto){
        if ( attractionBoardLikeService.removeAttractionBoardLike(attractionBoardLikeAddRequestDto.getMemberId()) == 0){
            attractionBoardLikeService.addAttractionBoardLike(attractionBoardLikeAddRequestDto);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeAttractionBoardLike(@PathVariable("memberId") long memberId){
        if ( attractionBoardLikeService.removeAttractionBoardLike(memberId) == 0){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/count/{attractionId}")
    public ResponseEntity<Integer> searchAttractionBoardLikeCount(@PathVariable("attractionId") int attractionId){
        log.info("attracationId : {}", attractionId);
        return ResponseEntity.ok(attractionBoardLikeService.searchAttractionBoardLikeCount(attractionId));
    }
}
