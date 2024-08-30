package com.shsoftvina.community.model.authenticate.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterReq {

    @Email(message = "email must be email")
    private String email;

    @NotBlank(message = "username must be not blank")
    private String username;

    @NotBlank(message = "phone must be not blank")
    private String phone;

    @NotBlank(message = "password must be not blank")
    private String password;
}
