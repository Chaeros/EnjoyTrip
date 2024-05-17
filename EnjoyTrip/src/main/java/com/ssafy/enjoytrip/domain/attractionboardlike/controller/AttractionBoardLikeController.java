package com.ssafy.enjoytrip.domain.attractionboardlike.controller;

import com.ssafy.enjoytrip.domain.attractionboardlike.AttractionBoardLike;
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
        log.info("[regist like] attractionBoardLikeAddRequestDto : {}",attractionBoardLikeAddRequestDto);
        if ( attractionBoardLikeService.removeAttractionBoardLike(AttractionBoardLike.builder()
                .memberId(attractionBoardLikeAddRequestDto.getMemberId())
                .attractionBoardId(attractionBoardLikeAddRequestDto.getAttractionBoardId())
                .build()) == 0 ){
            attractionBoardLikeService.addAttractionBoardLike(attractionBoardLikeAddRequestDto);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAttractionBoardLike(@RequestParam("memberId") long memberId,
                                                          @RequestParam("attractionBoardId") int attractionBoardId){
        log.info("[remove like] memberId : {}, attractionBoardId: {}",memberId,attractionBoardId);
        if ( attractionBoardLikeService.removeAttractionBoardLike(
                AttractionBoardLike.builder()
                        .memberId(memberId)
                        .attractionBoardId(attractionBoardId)
                        .build()) == 0 ){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/count/{attractionId}")
    public ResponseEntity<Integer> searchAttractionBoardLikeCount(@PathVariable("attractionId") int attractionId){
        log.info("attracationId : {}", attractionId);
        return ResponseEntity.ok(attractionBoardLikeService.searchAttractionBoardLikeCount(attractionId));
    }

    @GetMapping("/isLikeReview")
    public ResponseEntity<Void> isMyLikeAttractionBoardId(@RequestParam("memberId") long memberId,
                                               @RequestParam("attractionBoardId") int attractionBoardId){
        if ( attractionBoardLikeService.searchAttractionBoardLike(
                AttractionBoardLike.builder()
                        .memberId(memberId)
                        .attractionBoardId(attractionBoardId)
                        .build()) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
