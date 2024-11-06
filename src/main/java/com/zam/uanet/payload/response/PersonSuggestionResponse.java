package com.zam.uanet.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonSuggestionResponse {

    private String personId;
    private String userName;
    private String fullName;
    private String photo;

}
