package com.ssafy.enjoytrip.domain.unreadmessagecount.mapper;

import com.ssafy.enjoytrip.domain.friend.Friend;
import com.ssafy.enjoytrip.domain.unreadmessagecount.UnreadMessageCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnreadMessageCountMapper {
    int insertUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    int updateAddUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    int updateResetUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    Integer findUnreadMessageCount(UnreadMessageCount unreadMessageCount);
    List<UnreadMessageCount> findUnreadMessageCountListById(long myId);
}
