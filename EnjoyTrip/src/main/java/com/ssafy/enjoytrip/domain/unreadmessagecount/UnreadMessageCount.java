package com.ssafy.enjoytrip.domain.unreadmessagecount;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@ToString
public class UnreadMessageCount {
    int roomId;
    long receiverId;
    int count;
}
