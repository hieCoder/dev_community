package com.shsoftvina.community.config;

import com.shsoftvina.community.utils.PatternUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.shsoftvina.community.constant.ApplicationConstant.CLIENT_SERVER;

@Slf4j
@Component
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    public static Map<String, String> OAuth2Map = new HashMap<>();

    private final String EMAIL_ATTRIBUTE = "email";

    private final String USERNAME_ATTRIBUTE = "login";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User ) authentication.getPrincipal();
        String login = (String) oAuth2User.getAttributes().get(EMAIL_ATTRIBUTE);

        if(!PatternUtils.isEMail(login)){
            login =  (String) oAuth2User.getAttributes().get(USERNAME_ATTRIBUTE);
        }

        log.debug("Login success with login {}", login);

        String oauth2Id = UUID.randomUUID().toString();
        OAuth2Map.put(oauth2Id, login);

        response.sendRedirect(CLIENT_SERVER + "/social-success/" + oauth2Id);
    }
}
