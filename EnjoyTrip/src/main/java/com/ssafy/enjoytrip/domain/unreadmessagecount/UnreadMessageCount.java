package com.ssafy.enjoytrip.domain.unreadmessagecount;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnreadMessageCount {
    int roomId;
    long receiverId;
    int count;
    String lastEnterDate;
}
