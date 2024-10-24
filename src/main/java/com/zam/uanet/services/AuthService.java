package com.zam.uanet.services;


import com.zam.uanet.payload.request.SignInRequest;
import com.zam.uanet.payload.request.SignUpRequest;
import com.zam.uanet.payload.response.SignInResponse;
import com.zam.uanet.payload.response.SignUpResponse;

public interface AuthService {

    public SignUpResponse signUp(SignUpRequest signUpRequest);
    public SignInResponse signIn(SignInRequest signInRequest);

}
