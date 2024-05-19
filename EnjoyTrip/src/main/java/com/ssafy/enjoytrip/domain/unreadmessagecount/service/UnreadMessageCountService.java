package com.ssafy.enjoytrip.domain.unreadmessagecount.service;

import com.ssafy.enjoytrip.domain.unreadmessagecount.UnreadMessageCount;

public interface UnreadMessageCountService {
    int addUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    int modifyAddUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    int modifyResetUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    Integer searchUnreadMessageCount(UnreadMessageCount unreadMessageCount);
}
