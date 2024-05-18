package com.ssafy.enjoytrip.domain.chatmessage.service;

import com.ssafy.enjoytrip.domain.chatmessage.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    int addChatMessage(ChatMessage chatMessage);
    List<ChatMessage> searchChatMessageList(int roomId);
}
