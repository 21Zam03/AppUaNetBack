package com.zam.uanet.services;

import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.payload.response.PersonStartResponse;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;


public interface AccountService {

    public PersonStartResponse getPerson(ObjectId personId);
    public MessageResponse changePhotoProfile(String email, MultipartFile file) throws Exception;


}
