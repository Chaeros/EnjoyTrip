package com.ssafy.enjoytrip.domain.chatmessage.mapper;

import com.ssafy.enjoytrip.domain.chatmessage.ChatMessage;
import com.ssafy.enjoytrip.domain.chatmessage.dto.ChatMessageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    int insertChatMessage(ChatMessage chatMessage);
    List<ChatMessage> findChatMessageListByRoomId(int roomId);
}
