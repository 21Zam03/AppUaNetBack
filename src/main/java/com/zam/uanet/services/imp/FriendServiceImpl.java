package com.zam.uanet.services.imp;

import com.zam.uanet.dtos.FriendDTO;
import com.zam.uanet.entities.FriendEntity;
import com.zam.uanet.repositories.FriendRepository;
import com.zam.uanet.services.FriendService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Override
    public FriendDTO createFriend(FriendEntity friendEntity) {

        FriendEntity friend = friendRepository.save(friendEntity);
        FriendDTO friendDTO = new FriendDTO();
        friendDTO.setIdFriend(friend.getIdFriend().toHexString());
        friendDTO.setUserId1(friend.getUserId1().toHexString());
        friendDTO.setUserId2(friend.getUserId2().toHexString());
        friendDTO.setStatus(friend.getStatus());
        return null;

    }

    @Override
    public void deleteFriend(ObjectId idFriend) {
        friendRepository.deleteById(idFriend);
    }

    @Override
    public FriendDTO updateFriend(FriendEntity friendEntity) {
        FriendEntity friend = friendRepository.save(friendEntity);
        FriendDTO friendDTO = new FriendDTO();
        friendDTO.setIdFriend(friend.getIdFriend().toHexString());
        friendDTO.setUserId1(friend.getUserId1().toHexString());
        friendDTO.setUserId2(friend.getUserId2().toHexString());
        friendDTO.setStatus(friend.getStatus());
        return friendDTO;
    }

    @Override
    public FriendDTO findFriendsByUserId1AndUserId2(ObjectId userId1, ObjectId userId2) {
        System.out.println("LLegada");
        FriendEntity friendEntity = friendRepository.findFriendsByUserId1AndUserId2(userId1, userId2);
        if (friendEntity != null) {
            FriendDTO friendDTO = new FriendDTO();
            friendDTO.setIdFriend(friendEntity.getIdFriend().toHexString());
            friendDTO.setUserId1(friendEntity.getUserId1().toHexString());
            friendDTO.setUserId2(friendEntity.getUserId2().toHexString());
            friendDTO.setStatus(friendEntity.getStatus());
            return friendDTO;
        } else {
            return new FriendDTO();
        }
    }

    @Override
    public long countFriends(ObjectId userId) {
        return friendRepository.countFriends(userId);
    }
}
