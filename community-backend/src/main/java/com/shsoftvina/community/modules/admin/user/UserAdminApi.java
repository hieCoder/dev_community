package com.shsoftvina.community.modules.admin.user;

import com.shsoftvina.community.model.authenticate.login.JwtTokenRes;
import com.shsoftvina.community.modules.admin.user.model.req.ChangePasswordUserAdminReq;
import com.shsoftvina.community.modules.admin.user.model.req.EditUserAdminReq;
import com.shsoftvina.community.modules.admin.user.model.res.UserAdminRes;
import com.shsoftvina.community.modules.admin.user.service.UserAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserAdminApi {

    @Autowired
    private UserAdminService userAdminService;

    @GetMapping("/profile")
    public ResponseEntity<UserAdminRes> findDetail() {
        return ResponseEntity.ok(userAdminService.findDetail());
    }

    @PutMapping("/profile")
    public ResponseEntity<JwtTokenRes> editUser(@Valid @RequestBody EditUserAdminReq req){
        return ResponseEntity.ok(userAdminService.editUser(req));
    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordUserAdminReq req){
        userAdminService.changePassword(req);
        return ResponseEntity.noContent().build();
    }
}
