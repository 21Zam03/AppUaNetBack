package com.zam.uanet.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonStartResponse {

    private String personId;
    private String fullName;
    private String nickName;
    private LocalDate birthdate;
    private String genre;
    private String district;
    private String career;
    private String profilePhoto;

}
