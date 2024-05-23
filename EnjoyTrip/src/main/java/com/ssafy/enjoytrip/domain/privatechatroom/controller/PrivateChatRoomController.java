package com.ssafy.enjoytrip.domain.privatechatroom.controller;

import com.ssafy.enjoytrip.domain.privatechatroom.PrivateChatRoom;
import com.ssafy.enjoytrip.domain.privatechatroom.service.PrivateChatRoomService;
import com.ssafy.enjoytrip.domain.unreadmessagecount.UnreadMessageCount;
import com.ssafy.enjoytrip.domain.unreadmessagecount.service.UnreadMessageCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/privatechatroom")
public class PrivateChatRoomController {

    private final PrivateChatRoomService privateChatRoomService;
    private final UnreadMessageCountService unreadMessageCountService;

    @PostMapping
    public ResponseEntity<Integer> enterOrRegistPrivateChatRoom(@RequestBody PrivateChatRoom privateChatRoom){
        log.info("[enterOrRegistPrivateChatRoom] privateChatRoom : {}",privateChatRoom);
        if ( privateChatRoomService.searchPrivateChatRoom(privateChatRoom) == null ){
            ResponseEntity.ok(privateChatRoomService.addPrivateChatRoom(privateChatRoom));
            
            // 방마다 읽지 않은 개수 카운트하기 위한 데이터 삽입
            unreadMessageCountService.addUnreadMessageCount(
                    UnreadMessageCount.builder()
                            .roomId(privateChatRoom.getRoomId())
                            .receiverId(privateChatRoom.getMyId())
                            .build()
            );
            unreadMessageCountService.addUnreadMessageCount(
                    UnreadMessageCount.builder()
                            .roomId(privateChatRoom.getRoomId())
                            .receiverId(privateChatRoom.getOpponentId())
                            .build()
            );
        }
        return ResponseEntity.ok(privateChatRoomService.searchPrivateChatRoom(privateChatRoom));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<PrivateChatRoom>> bringPrivateChatRoomList(@PathVariable("memberId") long memberId){
        log.info("[bringPrivateChatRoomList] memberId : {}",memberId);
        return ResponseEntity.ok(privateChatRoomService.searchPrivateChatRoomList(memberId));
    }

    @GetMapping
    public ResponseEntity<Integer> searchPrivateChatRoom(@RequestParam("myId") long myId,
                                                                 @RequestParam("opponentId") long opponentId){
        log.info("[searchPrivateChatRoom] myId : {}, opponentId : {}",myId, opponentId);
        Integer result = privateChatRoomService.searchPrivateChatRoom(
                PrivateChatRoom.builder().myId(myId).opponentId(opponentId).build());
        if ( result == null ){
            log.info("value is empty");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
