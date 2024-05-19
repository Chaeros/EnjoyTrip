package com.ssafy.enjoytrip.domain.unreadmessagecount.controller;

import com.ssafy.enjoytrip.domain.unreadmessagecount.UnreadMessageCount;
import com.ssafy.enjoytrip.domain.unreadmessagecount.service.UnreadMessageCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unreadmessagecount")
public class UnreadMessageCountController {
    private UnreadMessageCountService unreadMessageCountService;

    @PostMapping
    public ResponseEntity<Void> registUnreadMessageCount(@RequestBody UnreadMessageCount unreadMessageCount){
        if ( unreadMessageCountService.addUnreadMessageCount(unreadMessageCount) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/addcount")
    public ResponseEntity<Void> countAddUnreadMessageCount(@RequestBody UnreadMessageCount unreadMessageCount){
        if ( unreadMessageCountService.modifyAddUnreadMessageCount(unreadMessageCount) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resetcount")
    public ResponseEntity<Void> countResetUnreadMessageCount(@RequestBody UnreadMessageCount unreadMessageCount){
        if ( unreadMessageCountService.modifyAddUnreadMessageCount(unreadMessageCount) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Integer> searchUnreadMessageCount(@RequestBody UnreadMessageCount unreadMessageCount){
        Integer result = unreadMessageCountService.searchUnreadMessageCount(unreadMessageCount);
        if ( result == null ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
