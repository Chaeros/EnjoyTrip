package com.ssafy.enjoytrip.domain.friend.controller;

import com.ssafy.enjoytrip.domain.friend.Friend;
import com.ssafy.enjoytrip.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<Void> registFriend(@RequestBody Friend friend){
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

}
