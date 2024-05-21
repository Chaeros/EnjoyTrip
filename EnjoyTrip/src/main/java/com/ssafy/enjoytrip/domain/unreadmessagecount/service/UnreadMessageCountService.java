package com.ssafy.enjoytrip.domain.unreadmessagecount.service;

import com.ssafy.enjoytrip.domain.unreadmessagecount.UnreadMessageCount;

import java.util.List;

public interface UnreadMessageCountService {
    int addUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    int modifyAddUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    int modifyResetUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    Integer searchUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    List<UnreadMessageCount> searchUnreadMessageCountListById(long myId);
}
