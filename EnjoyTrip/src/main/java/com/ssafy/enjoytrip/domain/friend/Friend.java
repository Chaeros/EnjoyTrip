package com.ssafy.enjoytrip.domain.friend;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Friend {
    private int id;
    private long myId;
    private long friendId;
}
