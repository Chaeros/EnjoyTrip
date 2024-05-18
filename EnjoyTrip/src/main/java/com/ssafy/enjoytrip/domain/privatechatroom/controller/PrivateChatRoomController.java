package com.ssafy.enjoytrip.domain.privatechatroom.controller;

import com.ssafy.enjoytrip.domain.privatechatroom.PrivateChatRoom;
import com.ssafy.enjoytrip.domain.privatechatroom.service.PrivateChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/privatechatroom")
public class PrivateChatRoomController {

    private final PrivateChatRoomService privateChatRoomService;

    @PostMapping
    public ResponseEntity<Integer> enterOrRegistPrivateChatRoom(@RequestBody PrivateChatRoom privateChatRoom){
        if ( privateChatRoomService.searchPrivateChatRoom(privateChatRoom) == 0 ){
            return ResponseEntity.ok(privateChatRoomService.addPrivateChatRoom(privateChatRoom));
        }
        return ResponseEntity.ok(privateChatRoomService.searchPrivateChatRoom(privateChatRoom));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<PrivateChatRoom>> bringPrivateChatRoomList(@PathVariable("memberId") long memberId){
        return ResponseEntity.ok(privateChatRoomService.searchPrivateChatRoomList(memberId));
    }
}
