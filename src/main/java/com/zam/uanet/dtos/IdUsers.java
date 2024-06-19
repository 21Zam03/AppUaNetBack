package com.zam.uanet.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdUsers {

    private ObjectId userId1;
    private ObjectId userId2;

}
