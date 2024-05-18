package com.ssafy.enjoytrip.domain.privatechatroom.service;

import com.ssafy.enjoytrip.domain.privatechatroom.PrivateChatRoom;
import com.ssafy.enjoytrip.domain.privatechatroom.mapper.PrivateChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateChatRoomServiceImpl implements PrivateChatRoomService{

    private final PrivateChatRoomMapper privateChatRoomMapper;

    @Override
    public int addPrivateChatRoom(PrivateChatRoom privateChatRoom) {
        return privateChatRoomMapper.insertPrivateChatRoom(privateChatRoom);
    }

    @Override
    public Integer searchPrivateChatRoom(PrivateChatRoom privateChatRoom) {
        return privateChatRoomMapper.findPrivateChatRoom(privateChatRoom);
    }

    @Override
    public List<PrivateChatRoom> searchPrivateChatRoomList(long memberId) {
        return privateChatRoomMapper.findPrivateChatRoomList(memberId);
    }
}
