package com.ssafy.enjoytrip.domain.privatechatroom.service;

import com.ssafy.enjoytrip.domain.privatechatroom.PrivateChatRoom;

import java.util.List;

public interface PrivateChatRoomService {
    int addPrivateChatRoom(PrivateChatRoom privateChatRoom);
    int searchPrivateChatRoom(PrivateChatRoom privateChatRoom);
    List<PrivateChatRoom> searchPrivateChatRoomList(long memberId);
}
