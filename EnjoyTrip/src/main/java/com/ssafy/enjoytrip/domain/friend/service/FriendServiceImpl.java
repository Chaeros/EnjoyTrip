package com.ssafy.enjoytrip.domain.friend.service;

import com.ssafy.enjoytrip.domain.friend.Friend;
import com.ssafy.enjoytrip.domain.friend.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendMapper friendMapper;

    @Override
    public int addFriend(Friend friend) {
        return friendMapper.insertFriend(friend);
    }

    @Override
    public int removeFriend(long id) {
        return friendMapper.deleteFriend(id);
    }

    @Override
    public int removeFriendByDto(Friend friend) {
        return friendMapper.deleteFriendByDto(friend);
    }

    @Override
    public List<Friend> searchFriendList(long myId) {
        return friendMapper.findFriendList(myId);
    }

    @Override
    public int searchDuplicateRelationship(Friend friend) {
        return friendMapper.findIsExistRelationship(friend);
    }

    @Override
    public List<Friend> searchFriendInfoByRoomIdAndMyId(Friend friend) {
        return friendMapper.findFriendInfoByRoomIdAndMyId(friend);
    }
}
