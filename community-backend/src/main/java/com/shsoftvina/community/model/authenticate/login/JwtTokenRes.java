package com.shsoftvina.community.model.authenticate.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@AllArgsConstructor
public class JwtTokenRes {

    private String idToken;
    private String idRefreshToken;
    private long refreshTokenValidSecond;
}
