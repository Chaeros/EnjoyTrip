package com.ssafy.enjoytrip.domain.chatmessage.service;

import com.ssafy.enjoytrip.domain.chatmessage.ChatMessage;
import com.ssafy.enjoytrip.domain.chatmessage.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public int addChatMessage(ChatMessage chatMessage) {
        return chatMessageMapper.insertChatMessage(chatMessage);
    }

    @Override
    public List<ChatMessage> searchChatMessageList(int roomId) {
        return chatMessageMapper.findChatMessageListByRoomId(roomId);
    }
}