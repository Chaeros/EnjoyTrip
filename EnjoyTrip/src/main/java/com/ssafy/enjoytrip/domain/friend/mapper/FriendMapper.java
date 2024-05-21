package com.ssafy.enjoytrip.domain.friend.mapper;

import com.ssafy.enjoytrip.domain.friend.Friend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendMapper {
    int insertFriend(Friend friend);
    int deleteFriend(long id);
    List<Friend> findFriendList(long myId);
    int findIsExistRelationship(Friend friend);
    int deleteFriendByDto(Friend friend);
    List<Friend> findFriendInfoByRoomIdAndMyId(Friend friend);
}
