package com.ssafy.enjoytrip.domain.friend;

import lombok.Builder;

@Builder
public class Friend {
    private int id;
    private long myId;
    private long friendId;
}
