package com.shsoftvina.community.modules.admin.user.service;

import com.shsoftvina.community.model.authenticate.login.JwtTokenRes;
import com.shsoftvina.community.modules.admin.user.model.req.ChangePasswordUserAdminReq;
import com.shsoftvina.community.modules.admin.user.model.req.EditUserAdminReq;
import com.shsoftvina.community.modules.admin.user.model.res.UserAdminRes;
import com.shsoftvina.community.modules.root.user.service.UserService;

public interface UserAdminService extends UserService {
    UserAdminRes findDetail();
    JwtTokenRes editUser(EditUserAdminReq req);
    void changePassword(ChangePasswordUserAdminReq req);
}
