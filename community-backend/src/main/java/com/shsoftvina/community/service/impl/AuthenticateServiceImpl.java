package com.shsoftvina.community.service.impl;

import com.shsoftvina.community.config.JwtProvider;
import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.domain.enumration.EGroupNoti;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.mapper.authenticate.RegisterReqMapper;
import com.shsoftvina.community.model.authenticate.forgotpass.ForgotPassRes;
import com.shsoftvina.community.model.authenticate.login.JwtTokenRes;
import com.shsoftvina.community.model.authenticate.register.RegisterReq;
import com.shsoftvina.community.modules.group_noti.service.GroupNotiDevService;
import com.shsoftvina.community.modules.root.user.service.UserService;
import com.shsoftvina.community.service.AuthenticateService;
import com.shsoftvina.community.utils.ApplicationUtils;
import com.shsoftvina.community.utils.PatternUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;

import static com.shsoftvina.community.domain.enumration.EGroupNoti.ACCOUNT_ACTIVITY;
import static com.shsoftvina.community.domain.enumration.EGroupNoti.SOCIAL_INTERACTIONS;
import static com.shsoftvina.community.domain.enumration.ERole.ADMIN;
import static com.shsoftvina.community.domain.enumration.EStatus.ACTIVATED;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    private Map<String, RegisterReq> userRegisterCache = new HashMap<>();
    private Map<String, ForgotPassRes> userForgotPassCache = new HashMap<>();

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterReqMapper registerReqMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GroupNotiDevService groupNotiDevService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtDecoder jwtDecoder;

    private final String USERNAME_PREFIX_OAUTH2 = "user_";

    @Override
    public String registerUser(RegisterReq req) {
        String usernameReq = req.getUsername().trim().toLowerCase();
        String emailReq = req.getEmail().trim().toLowerCase();

        this.validateNewUser(usernameReq, emailReq);

        String token = UUID.randomUUID().toString();
        userRegisterCache.put(token, req);
        return token;
    }

    private List<EGroupNoti> getAllGroup(){
        return List.of(ACCOUNT_ACTIVITY, SOCIAL_INTERACTIONS);
    }

    @Override
    public boolean confirmRegisterUser(String token) {
        if (userRegisterCache.containsKey(token)) {
            RegisterReq req = userRegisterCache.get(token);

            User user = registerReqMapper.toEntity(req);
            user.setRole(ADMIN);
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setStatus(ACTIVATED);
            user.setGroups(groupNotiDevService.findByCodes(this.getAllGroup()));
            userService.save(user);
            userRegisterCache.remove(token);
            return true;
        }
        return false;
    }

    @Override
    public JwtTokenRes getTokenOAuth2(String login) {

        JwtTokenRes token = null;

        Optional<User> userOptional = null;
        if(PatternUtils.isEMail(login)){
            userOptional = userService.findByEmail(login);
        } else {
            userOptional = userService.findByUsernameOAuth2(login);
        }


        if(userOptional.isPresent()){
            token = jwtProvider.createToken(userOptional.get());
        } else {
            User user = new User();

            if(PatternUtils.isEMail(login)){
                user.setEmail(login);
                user.setUsername(USERNAME_PREFIX_OAUTH2 + ApplicationUtils.generateCodeInt());
            } else {
                user.setEmail(null);
                if(userService.existsByUsername(login)){
                    user.setUsername(login + "_" + ApplicationUtils.generateCodeInt());
                } else {
                    user.setUsername(login);
                }
            }

            user.setPassword(UUID.randomUUID().toString());
            user.setRole(ADMIN);
            user.setStatus(ACTIVATED);
            user.setGroups(groupNotiDevService.findByCodes(this.getAllGroup()));
            token = jwtProvider.createToken(userService.save(user));
        }

        return token;
    }

    @Override
    public ForgotPassRes forgotPassUser(String email) {

        if(!userService.existsByEmail(email)){
            throw new BadRequestAlertException(ErrorEnum.USER_NOT_FOUND);
        }

        String token = UUID.randomUUID().toString();
        String newPassword = ApplicationUtils.generateCodeInt();

        ForgotPassRes forgotPassRes = new ForgotPassRes(token, email, newPassword);
        userForgotPassCache.put(token, forgotPassRes);
        return forgotPassRes;
    }

    @Override
    public JwtTokenRes getTokenConfirmForgotPassUser(String token) {

        ForgotPassRes forgotPass = userForgotPassCache.get(token);

        User user = userService.getByEmail(forgotPass.getEmail());
        user.setPassword(passwordEncoder.encode(forgotPass.getNewPassword()));

        userForgotPassCache.remove(token);

        return jwtProvider.createToken(userService.save(user));
    }

    @Override
    public JwtTokenRes refreshToken(String refreshToken) {

        Jwt jwt = jwtDecoder.decode(refreshToken);

        if (jwt.getExpiresAt().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token has expired");
        }

        User user = userService.findByUsername(jwt.getSubject());
        JwtTokenRes newToken = jwtProvider.createToken(user);
        return newToken;
    }

    private void validateNewUser(String username, String email){
        if(userService.existsByUsername(username)) throw new BadRequestAlertException(ErrorEnum.USER_NAME_ALREADY_EXISTED);
        if(userService.existsByEmail(email)) throw new BadRequestAlertException(ErrorEnum.EMAIL_ALREADY_EXISTED);
    }
}