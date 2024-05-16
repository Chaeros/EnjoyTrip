package com.ssafy.enjoytrip.domain.friend.service;

import com.ssafy.enjoytrip.domain.friend.Friend;

import java.util.List;

public interface FriendService {
    int addFriend(Friend friend);
    int removeFriend(long id);
    List<Friend> searchFriendList(long myId);
}
