package com.ssafy.enjoytrip.domain.privatechatroom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrivateChatRoom {
    int roomId;
    long myId;
    long opponentId;
    String regdate;
    String recentData;
}
