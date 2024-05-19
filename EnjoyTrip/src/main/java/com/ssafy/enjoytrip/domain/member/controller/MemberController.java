package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.friend.Friend;
import com.ssafy.enjoytrip.domain.friend.service.FriendService;
import com.ssafy.enjoytrip.domain.member.dto.request.MemberSignUpDto;
import com.ssafy.enjoytrip.domain.member.dto.request.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.dto.response.MemberResponseDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.domain.privatechatroom.PrivateChatRoom;
import com.ssafy.enjoytrip.domain.privatechatroom.service.PrivateChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final FriendService friendService;
    private final PrivateChatRoomService privateChatRoomService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody MemberSignUpDto memberSignUpDto) throws Exception{
        log.info("[sign-up] memberSignUpDto : {}",memberSignUpDto);
        memberService.signUp(memberSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest(){
        return "jwtTest 요청 성공";
    }

    @PostMapping("/oauth2/sign-up")
    public void signUp(@RequestBody MemberUpdateDto memberUpdateDto) throws Exception {
        log.info("memberUpdateDto : {}",memberUpdateDto);
        memberService.updateMember(memberUpdateDto);
    }

    @GetMapping("/member/{email}")
    public ResponseEntity<MemberResponseDto> getMemberByEmail(@PathVariable("email") String email) throws Exception {
        log.info("[get member by email] memberEmail : {}",email);
        return memberService.getMemberByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/member/id/{id}")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable("id") long id) throws Exception {
        log.info("[get member by id] memberId : {}", id);
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    // 친구 추가를 할 때, 유저가 이미 친구 추가한 상대는 받아오지 않으면서 키워드에 알맞는 유저를 반환합니다.
    @GetMapping("/member/keyword")
    public ResponseEntity<List<MemberResponseDto>> getMemberByNicnnameKeyword(@RequestParam("keyword") String keyword,
                                                                              @RequestParam("userId") long userId) throws Exception {
        log.info("[get member by keyword] keyword : {}", keyword);
        List<Friend> friends = friendService.searchFriendList(userId);

        // friends 리스트의 friendId 값을 수집합니다.
        Set<Long> friendIds = friends.stream()
                .map(Friend::getFriendId)
                .collect(Collectors.toSet());

        // memberService에서 keyword에 해당하는 멤버를 가져오고, 친구 목록에 없는 멤버만 필터링합니다.
        List<MemberResponseDto> filteredMembers = memberService.getMemberByNicknameKeyword(keyword)
                .orElse(Collections.emptyList())
                .stream()
                .filter(member -> !friendIds.contains(member.getId()) && member.getId() != userId)
                .collect(Collectors.toList());

        if (filteredMembers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(filteredMembers);
        }
    }

    @GetMapping("/member/chat/{memberId}")
    public ResponseEntity<List<Long>> getChattingMemberId(@PathVariable("memberId") long memberId) throws Exception {
        List<PrivateChatRoom> privateChatRooms = privateChatRoomService.searchPrivateChatRoomList(memberId);
        List<Long> opponentMembers = new ArrayList<>();
        for ( PrivateChatRoom privateChatRoom : privateChatRooms ){
            if ( privateChatRoom.getMyId() != memberId){
                opponentMembers.add(privateChatRoom.getMyId());
            }
            else{
                opponentMembers.add(privateChatRoom.getOpponentId());
            }
        }
        return ResponseEntity.ok(opponentMembers);
    }
}
