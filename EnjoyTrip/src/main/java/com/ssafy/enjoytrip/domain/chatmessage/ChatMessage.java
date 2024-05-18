package com.ssafy.enjoytrip.domain.chatmessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    private int messageId;
    private Long roomId;
    private Long memberId;
    private String message;
    private String regdate;
}
