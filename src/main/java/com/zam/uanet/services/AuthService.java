package com.zam.uanet.services;


import com.zam.uanet.payload.request.InfoUserRequest;
import com.zam.uanet.payload.request.SignInRequest;
import com.zam.uanet.payload.request.SignUpRequest;
import com.zam.uanet.payload.response.SignInResponse;
import com.zam.uanet.payload.response.SignUpResponse;
import com.zam.uanet.payload.response.UserLoggedResponse;

public interface AuthService {

    public SignUpResponse signUp(SignUpRequest signUpRequest);
    public SignInResponse signIn(SignInRequest signInRequest);
    public UserLoggedResponse validateSession(String token);
    public UserLoggedResponse addInfoUser(InfoUserRequest infoUserRequest);

}
