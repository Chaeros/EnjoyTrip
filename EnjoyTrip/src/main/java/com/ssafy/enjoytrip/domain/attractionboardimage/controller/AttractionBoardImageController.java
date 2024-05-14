package com.ssafy.enjoytrip.domain.attractionboardimage.controller;

import com.ssafy.enjoytrip.domain.attractionboardimage.AttractionBoardImage;
import com.ssafy.enjoytrip.domain.attractionboardimage.dto.request.AttractionBoardImageAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardimage.service.AttractionBoardImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attractionboardimage")
public class AttractionBoardImageController {

    private final AttractionBoardImageService attractionBoardImageService;

    @PostMapping
    public ResponseEntity<String> registAttractionBoardImage(@RequestBody AttractionBoardImageAddRequestDto attractionBoardImageAddRequestDto){
        if ( attractionBoardImageService.addAttractionBoardImage(attractionBoardImageAddRequestDto.transferToAttractionBoardImage()) == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image registration failed due to incorrect data or server error.");
        }
        return ResponseEntity.ok("Image store success");
    }

    @GetMapping("/{attractionBoardId}")
    public ResponseEntity<AttractionBoardImage> searchAttractionBoardImage(@PathVariable("attractionBoardId") int attractionBoardId){
        return attractionBoardImageService.searchAttractionBoardImage(attractionBoardId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}
