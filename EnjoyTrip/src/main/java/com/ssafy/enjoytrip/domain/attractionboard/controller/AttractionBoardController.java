package com.ssafy.enjoytrip.domain.attractionboard.controller;

import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;
import com.ssafy.enjoytrip.domain.attractionboard.service.AttractionBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attractionboard")
@RequiredArgsConstructor
@Slf4j
public class AttractionBoardController {
    private final AttractionBoardService attractionBoardService;

    @PostMapping
    public ResponseEntity<Void> registAttractionBoard(@RequestBody AttractionBoardAddRequestDto attractionBoardAddRequestDto){
        log.info("attractionBoardAddRequestDto: {}", attractionBoardAddRequestDto);
        if ( attractionBoardService.addAttractionBoard(attractionBoardAddRequestDto) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> modifyAttractionBoard(@RequestBody AttractionBoardAddRequestDto attractionBoardAddRequestDto){
        log.info("[modifyAttractionBoard] attractionBoardAddRequestDto : {}",attractionBoardAddRequestDto);
        if ( attractionBoardService.modifyAttractionBoard(attractionBoardAddRequestDto) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{attractionBoardId}")
    public ResponseEntity<Void> removeAttractionBoard(@PathVariable("attractionBoardId") int attractionBoardId){
        if ( attractionBoardService.removeAttractionBoard(attractionBoardId) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hit/{attractionBoardId}")
    public ResponseEntity<Void> addHitCount(@PathVariable("attractionBoardId") int attractionBoardId){
        if ( attractionBoardService.addHitCount(attractionBoardId) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AttractionBoardResponseDto>> searchAttractionBoards(){
        return attractionBoardService.searchAttractionBoards()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/{attractionId}")
    public ResponseEntity<AttractionBoardResponseDto> searchAttractionBoard(@PathVariable("attractionId") int attractionId){
        return attractionBoardService.searchAttractionBoard(attractionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<AttractionBoardResponseDto>> searchAttractionBoardsByTitle(@PathVariable("title") String title){
        return attractionBoardService.searchAttractionBoardsByTitle(title)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/attractionId/{attractionId}")
    public ResponseEntity<List<AttractionBoardResponseDto>> searchAttractionBoardsByAttractionId(@PathVariable("attractionId") int attractionId){
        return attractionBoardService.searchAttractionBoardsByAttractionId(attractionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    // 페이지에 속하는 관광지 리뷰 목록을 불러오는 API
    @GetMapping("/page/{page}")
    public ResponseEntity<List<AttractionBoardResponseDto>> searchAttractionBoardsByPageNumber(@PathVariable("page") int page){
        log.info("[searchAttractionBoardsByPageNumber] page : {}",page);
        return attractionBoardService.searchPageOfAttractionBoard(page-1)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    // 전체 관광지 리뷰 개수를 불러오는 API
    @GetMapping("/attractionboardcount")
    public ResponseEntity<Integer> searchTotalCountOfAttractionBoards(){
        return ResponseEntity.ok(attractionBoardService.searchTotalCountOfAttractionBoard());
    }
}
