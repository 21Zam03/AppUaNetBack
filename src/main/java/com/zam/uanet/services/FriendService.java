package com.zam.uanet.services;

import com.zam.uanet.dtos.FriendDTO;
import com.zam.uanet.entities.FriendEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface FriendService {

    public FriendDTO createFriend(FriendEntity friendEntity);
    public void deleteFriend(ObjectId idFriend);
    public FriendDTO updateFriend(FriendEntity friendEntity);

    FriendDTO findFriendsByUserId1AndUserId2(ObjectId userId1, ObjectId userId2);
    long countFriends(ObjectId userId);
}
