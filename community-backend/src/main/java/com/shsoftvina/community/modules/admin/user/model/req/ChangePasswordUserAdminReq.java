package com.shsoftvina.community.modules.admin.user.model.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordUserAdminReq {

    @NotBlank(message = "currentPassword is not blank")
    private String currentPassword;
    @NotBlank(message = "newPassword is not blank")
    private String newPassword;
}