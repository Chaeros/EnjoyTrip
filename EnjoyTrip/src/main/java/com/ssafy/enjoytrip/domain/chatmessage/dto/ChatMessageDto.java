package com.ssafy.enjoytrip.domain.chatmessage.dto;

import com.ssafy.enjoytrip.domain.chatmessage.MessageType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatMessageDto {
    private MessageType messageType;
    private Long chatRoomId;
    private Long senderId;
    private String message;
}
