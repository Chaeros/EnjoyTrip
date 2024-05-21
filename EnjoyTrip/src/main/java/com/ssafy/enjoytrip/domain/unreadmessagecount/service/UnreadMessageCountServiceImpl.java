package com.ssafy.enjoytrip.domain.unreadmessagecount.service;

import com.ssafy.enjoytrip.domain.unreadmessagecount.UnreadMessageCount;
import com.ssafy.enjoytrip.domain.unreadmessagecount.mapper.UnreadMessageCountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnreadMessageCountServiceImpl implements UnreadMessageCountService{
    private final UnreadMessageCountMapper unreadMessageCountMapper;

    @Override
    public int addUnreadMessageCount(UnreadMessageCount unreadMessageCount) {
        return unreadMessageCountMapper.insertUnreadMessageCount(unreadMessageCount);
    }

    @Override
    public int modifyAddUnreadMessageCount(UnreadMessageCount unreadMessageCount) {
        return unreadMessageCountMapper.updateAddUnreadMessageCount(unreadMessageCount);
    }

    @Override
    public int modifyResetUnreadMessageCount(UnreadMessageCount unreadMessageCount) {
        return unreadMessageCountMapper.updateResetUnreadMessageCount(unreadMessageCount);
    }

    @Override
    public Integer searchUnreadMessageCount(UnreadMessageCount unreadMessageCount) {
        return unreadMessageCountMapper.findUnreadMessageCount(unreadMessageCount);
    }

    @Override
    public List<UnreadMessageCount> searchUnreadMessageCountListById(long myId) {
        return unreadMessageCountMapper.findUnreadMessageCountListById(myId);
    }
}
