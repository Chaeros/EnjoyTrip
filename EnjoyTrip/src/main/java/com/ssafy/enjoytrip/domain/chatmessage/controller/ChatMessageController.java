package com.ssafy.enjoytrip.domain.chatmessage.controller;

import com.ssafy.enjoytrip.domain.chatmessage.ChatMessage;
import com.ssafy.enjoytrip.domain.chatmessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatmessage")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<Void> registChatMessage(@RequestBody ChatMessage chatMessage){
        if ( chatMessageService.addChatMessage(chatMessage) == 0 ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<ChatMessage>> searchChatMessageList(@PathVariable("roomId") int roomId){
        return ResponseEntity.ok(chatMessageService.searchChatMessageList(roomId));
    }

}
