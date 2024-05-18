package com.ssafy.enjoytrip.domain.privatechatroom.mapper;

import com.ssafy.enjoytrip.domain.privatechatroom.PrivateChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrivateChatRoomMapper {
    int insertPrivateChatRoom(PrivateChatRoom privateChatRoom);
    Integer findPrivateChatRoom(PrivateChatRoom privateChatRoom);
    List<PrivateChatRoom> findPrivateChatRoomList(long memberId);
}
