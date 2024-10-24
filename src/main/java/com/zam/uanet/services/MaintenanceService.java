package com.zam.uanet.services;

import com.zam.uanet.payload.response.MessageResponse;
import org.bson.types.ObjectId;

public interface MaintenanceService {

    public MessageResponse createRole(String roleName);
    public MessageResponse deleteRole(ObjectId roleId);
    public MessageResponse updateRole(ObjectId roleId, String roleName);

}
