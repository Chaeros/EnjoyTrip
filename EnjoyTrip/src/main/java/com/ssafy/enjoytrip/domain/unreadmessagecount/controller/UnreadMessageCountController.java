package com.ssafy.enjoytrip.domain.unreadmessagecount.controller;

import com.ssafy.enjoytrip.domain.unreadmessagecount.UnreadMessageCount;
import com.ssafy.enjoytrip.domain.unreadmessagecount.service.UnreadMessageCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/unreadmessagecount")
public class UnreadMessageCountController {
    private final UnreadMessageCountService unreadMessageCountService;

    @PostMapping
    public ResponseEntity<Void> registUnreadMessageCount(@RequestBody UnreadMessageCount unreadMessageCount){
        log.info("[registUnreadMessageCount] unreadMessageCount : {}",unreadMessageCount);
        if ( unreadMessageCountService.addUnreadMessageCount(unreadMessageCount) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/addcount")
    public ResponseEntity<Void> countAddUnreadMessageCount(@RequestBody UnreadMessageCount unreadMessageCount){
        log.info("[countAddUnreadMessageCount] unreadMessageCount : {}",unreadMessageCount);
        if ( unreadMessageCountService.modifyAddUnreadMessageCount(unreadMessageCount) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/resetcount")
    public ResponseEntity<Void> countResetUnreadMessageCount(@RequestBody UnreadMessageCount unreadMessageCount){
        log.info("[countResetUnreadMessageCount] unreadMessageCount : {}",unreadMessageCount);
        if ( unreadMessageCountService.modifyResetUnreadMessageCount(unreadMessageCount) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Integer> searchUnreadMessageCount(@RequestParam("roomId") int roomId,
                                                            @RequestParam("receiverId") long receiverId){
        log.info("[searchUnreadMessageCount] roomId : {}, receiverId : {}",roomId, receiverId);
        Integer result = unreadMessageCountService.searchUnreadMessageCount(
                UnreadMessageCount.builder().roomId(roomId).receiverId(receiverId).build()
        );
        if ( result == null ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
