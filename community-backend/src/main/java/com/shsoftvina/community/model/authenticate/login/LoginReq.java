package com.shsoftvina.community.model.authenticate.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReq {

    @NotBlank(message = "username must be not blank")
    private String username;

    @NotBlank(message = "password must be not blank")
    private String password;
}
