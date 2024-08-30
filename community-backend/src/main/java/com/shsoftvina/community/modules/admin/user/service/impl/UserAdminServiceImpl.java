package com.shsoftvina.community.modules.admin.user.service.impl;

import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.model.authenticate.login.JwtTokenRes;
import com.shsoftvina.community.modules.admin.notification.service.NotificationAdminService;
import com.shsoftvina.community.modules.admin.user.mapper.EditUserAdminReqMapper;
import com.shsoftvina.community.modules.admin.user.mapper.UserAdminResMapper;
import com.shsoftvina.community.modules.admin.user.model.req.ChangePasswordUserAdminReq;
import com.shsoftvina.community.modules.admin.user.model.req.EditUserAdminReq;
import com.shsoftvina.community.modules.admin.user.model.res.UserAdminRes;
import com.shsoftvina.community.modules.admin.user.service.UserAdminService;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.modules.root.user.service.impl.UserServiceImpl;
import com.shsoftvina.community.security.SecurityUtil;
import com.shsoftvina.community.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAdminServiceImpl extends UserServiceImpl implements UserAdminService {

    @Autowired
    private UserAdminResMapper userAdminResMapper;

    @Autowired
    private EditUserAdminReqMapper editUserAdminReqMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationAdminService notificationAdminService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserAdminRes findDetail() {
        return userAdminResMapper.toDto(super.findByUsername(SecurityUtil.getUsernameCurrent()));
    }

    @Override
    public JwtTokenRes editUser(EditUserAdminReq req) {

        User user =  super.findByUsername(SecurityUtil.getUsernameCurrent());

        String username = req.getUsername().trim();
        if(!user.getUsername().equalsIgnoreCase(username) && super.existsByUsername(username)) throw new BadRequestAlertException(ErrorEnum.USER_NAME_ALREADY_EXISTED);

        editUserAdminReqMapper.partialUpdate(user, req);
        user = super.save(user);
        return jwtProvider.createToken(user);
    }

    @Override
    public void changePassword(ChangePasswordUserAdminReq req) {
        String currentPasswordReq = req.getCurrentPassword();
        String newPasswordReq = req.getNewPassword();

        String username = SecurityUtil.getUsernameCurrent();
        User user = super.findByUsername(username);
        if(!passwordEncoder.matches(currentPasswordReq, user.getPassword())){
            throw new BadRequestAlertException(ErrorEnum.PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(newPasswordReq));
        super.save(user);

        notificationAdminService.createNotification(CreateNotificationReq.buildForChangePassword(user));
    }
}