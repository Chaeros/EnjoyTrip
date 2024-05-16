package com.ssafy.enjoytrip.domain.friend.dto.request;

import lombok.Data;

@Data
public class FriendAddDto {
    private long myId;
    private long friendId;
}
