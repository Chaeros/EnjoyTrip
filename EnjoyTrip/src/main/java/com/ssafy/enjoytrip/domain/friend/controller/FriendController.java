package com.ssafy.enjoytrip.domain.friend.controller;

import com.ssafy.enjoytrip.domain.friend.Friend;
import com.ssafy.enjoytrip.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/friend")
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<Void> registFriend(@RequestBody Friend friend){
        log.info("[regist friend] friend {} :",friend);
        if ( friendService.addFriend(friend) == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFriend(@PathVariable("id") long id){
        if ( friendService.removeFriend(id) == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{myId}")
    public ResponseEntity<List<Friend>> searchMyFriends(@PathVariable("myId") long myId){
        return ResponseEntity.ok(friendService.searchFriendList(myId));
    }

    @GetMapping
    public ResponseEntity<Void> isMyFriend(@RequestParam("myId") long myId, @RequestParam("friendId") long friendId){
        if ( friendService.searchDuplicateRelationship(Friend.builder().myId(myId).friendId(friendId).build()) > 0 ){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFriendByDto(@RequestBody Friend friend){
        if ( friendService.removeFriendByDto(friend) == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/countinfo")
    public ResponseEntity<List<Friend>> searchFriendInfoByRoomIdAndMyId(@RequestParam("myId") long myId){
        return ResponseEntity.ok(friendService.searchFriendInfoByRoomIdAndMyId(
                Friend.builder().myId(myId).build()
        ));
    }
}
