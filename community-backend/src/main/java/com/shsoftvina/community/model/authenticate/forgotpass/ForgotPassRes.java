package com.shsoftvina.community.model.authenticate.forgotpass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ForgotPassRes{

    private String token;
    private String email;
    private String newPassword;
}
