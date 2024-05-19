package com.ssafy.enjoytrip.domain.unreadmessagecount.mapper;

import com.ssafy.enjoytrip.domain.unreadmessagecount.UnreadMessageCount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UnreadMessageCountMapper {
    int insertUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    int updateAddUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    int updateResetUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    Integer findUnreadMessageCount(UnreadMessageCount unreadMessageCount);
}
