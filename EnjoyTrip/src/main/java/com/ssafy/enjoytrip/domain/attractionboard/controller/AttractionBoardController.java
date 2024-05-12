package com.ssafy.enjoytrip.domain.attractionboard.controller;

import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.request.AttractionBoardModifyDto;
import com.ssafy.enjoytrip.domain.attractionboard.dto.response.AttractionBoardResponseDto;
import com.ssafy.enjoytrip.domain.attractionboard.service.AttractionBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attractionboard")
@RequiredArgsConstructor
public class AttractionBoardController {
    private final AttractionBoardService attractionBoardService;

    @PostMapping
    public ResponseEntity<Void> registAttractionBoard(@RequestBody AttractionBoardAddRequestDto attractionBoardAddRequestDto){
        if ( attractionBoardService.addAttractionBoard(attractionBoardAddRequestDto) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> modifyAttractionBoard(@RequestBody AttractionBoardModifyDto attractionBoardModifyDto){
        if ( attractionBoardService.modifyAttractionBoard(attractionBoardModifyDto) == 0 ){
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
}
