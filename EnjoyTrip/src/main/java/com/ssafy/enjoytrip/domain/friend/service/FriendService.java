package com.ssafy.enjoytrip.domain.friend.service;

import com.ssafy.enjoytrip.domain.friend.Friend;

import java.util.List;

public interface FriendService {
    int addFriend(Friend friend);
    int removeFriend(long id);
    int removeFriendByDto(Friend friend);
    List<Friend> searchFriendList(long myId);
    int searchDuplicateRelationship(Friend friend);
}
