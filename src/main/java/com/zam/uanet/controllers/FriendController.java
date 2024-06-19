package com.zam.uanet.controllers;

import com.zam.uanet.dtos.FriendDTO;
import com.zam.uanet.entities.FriendEntity;
import com.zam.uanet.services.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friends")
@Slf4j
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public FriendDTO createFriend(@RequestBody FriendEntity friend) {
        return friendService.createFriend(friend);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@PathVariable(value = "id") ObjectId idFriend) {
        friendService.deleteFriend(idFriend);
    }

    @GetMapping("/listFriends/{id1}/{id2}")
    @ResponseStatus(HttpStatus.OK)
    public FriendDTO findFriendsByUserId1AndUserId2(
            @PathVariable(value = "id1") ObjectId idUser1,
            @PathVariable(value = "id2") ObjectId idUser2) {
        return friendService.findFriendsByUserId1AndUserId2(idUser1, idUser2);
    }

}
