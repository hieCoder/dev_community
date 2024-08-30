package com.shsoftvina.community.service;

import com.shsoftvina.community.model.authenticate.forgotpass.ForgotPassRes;
import com.shsoftvina.community.model.authenticate.login.JwtTokenRes;
import com.shsoftvina.community.model.authenticate.register.RegisterReq;

public interface AuthenticateService {

    String registerUser(RegisterReq req);
    boolean confirmRegisterUser(String token);
    JwtTokenRes getTokenOAuth2(String login);
    ForgotPassRes forgotPassUser(String email);
    JwtTokenRes getTokenConfirmForgotPassUser(String token);
    JwtTokenRes refreshToken(String refreshToken);
}
