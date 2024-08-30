package com.shsoftvina.community.api;

import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.model.authenticate.forgotpass.ForgotPassRes;
import com.shsoftvina.community.model.authenticate.login.JwtTokenRes;
import com.shsoftvina.community.model.authenticate.login.LoginReq;
import com.shsoftvina.community.model.authenticate.register.RegisterReq;
import com.shsoftvina.community.service.AuthenticateService;
import com.shsoftvina.community.service.MailService;
import com.shsoftvina.community.config.JwtProvider;
import com.shsoftvina.community.config.Oauth2SuccessHandler;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/authenticate")
public class AuthenticateApi {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private AuthenticateService authenticateService;

    @Autowired
    private MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenRes> authorize(@Valid @RequestBody LoginReq req) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                req.getUsername(),
                req.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtTokenRes jwt = jwtProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt.getIdToken());
        return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/refresh-token/{refreshToken}")
    public ResponseEntity<JwtTokenRes> refreshToken(@PathVariable String refreshToken){
        return ResponseEntity.ok(authenticateService.refreshToken(refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterReq req) {
        String token = authenticateService.registerUser(req);
        if(token != null){
            Locale locale = LocaleContextHolder.getLocale();
            mailService.sendUriConfirmRegisterUser(req.getEmail(), token, locale);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/confirm/user-register/{token}")
    public ResponseEntity<String> confirmRegisterUser(@PathVariable String token) {
        return ResponseEntity.ok( authenticateService.confirmRegisterUser(token) ? "Success": "Error");
    }

    @PostMapping("/forgot-pass/{email}")
    public ResponseEntity<Void> forgotPassUser(@PathVariable String email) {
        ForgotPassRes forgotPassRes = authenticateService.forgotPassUser(email);
        if(forgotPassRes != null){
            Locale locale = LocaleContextHolder.getLocale();
            mailService.sendUriConfirmForgotPassUser(email, forgotPassRes, locale);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/confirm/user-forgot-pass/{token}")
    public ResponseEntity<?> getTokenConfirmForgotPassUser(@PathVariable String token) {

        JwtTokenRes jwtToken = authenticateService.getTokenConfirmForgotPassUser(token);
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/social-success/{oauth2Id}")
    public ResponseEntity<?> getTokenOAuth2(@PathVariable String oauth2Id) {

        String login = Oauth2SuccessHandler.OAuth2Map.get(oauth2Id);
        if(StringUtils.isBlank(login)){
           throw new BadRequestAlertException(ErrorEnum.ID_OAUTH2_NOT_FOUND);
        }

        Oauth2SuccessHandler.OAuth2Map.remove(oauth2Id);

        return ResponseEntity.ok(authenticateService.getTokenOAuth2(login));
    }
}