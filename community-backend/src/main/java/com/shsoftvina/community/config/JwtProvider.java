package com.shsoftvina.community.config;

import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.model.authenticate.login.JwtTokenRes;
import com.shsoftvina.community.modules.root.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

import static com.shsoftvina.community.config.SecurityUtils.AUTHORITIES_KEY;
import static com.shsoftvina.community.config.SecurityUtils.JWT_ALGORITHM;

@Component
public class JwtProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${community.security.authentication.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${community.security.authentication.jwt.refresh-token-validity-in-seconds:0}")
    private long refreshTokenValidityInSeconds;

    private final String ID_ATTRIBUTE_NAME_TOKEN_RES = "id";

    private final String EMAIL_ATTRIBUTE_NAME_TOKEN_RES = "email";

    private final String AVATAR_ATTRIBUTE_NAME_TOKEN_RES = "avatar";

    public JwtTokenRes createToken(Authentication authentication) {

        String accessToken = this.generateToken(authentication, tokenValidityInSeconds, false);
        String refreshToken = this.generateToken(authentication, refreshTokenValidityInSeconds, true);

        return new JwtTokenRes(accessToken, refreshToken, refreshTokenValidityInSeconds);
    }

    public JwtTokenRes createToken(User userDetails) {

        String accessToken = this.generateToken(userDetails, tokenValidityInSeconds, false);
        String refreshToken = this.generateToken(userDetails, refreshTokenValidityInSeconds, true);

        return new JwtTokenRes(accessToken, refreshToken, refreshTokenValidityInSeconds);
    }

    private String generateToken(Authentication authentication, long tokenValidityInSeconds, boolean isRefreshToken) {

        User userDetails = (User) authentication.getPrincipal();

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity = now.plus(Duration.ofSeconds(tokenValidityInSeconds));

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName());
        if (!isRefreshToken) {
            claimsBuilder.claim(AUTHORITIES_KEY, authorities);
        }

        if(!isRefreshToken) {
            claimsBuilder.claims(claims -> {
                claims.put(ID_ATTRIBUTE_NAME_TOKEN_RES, userDetails.getId());
                claims.put(EMAIL_ATTRIBUTE_NAME_TOKEN_RES, userDetails.getEmail());
                claims.put(AVATAR_ATTRIBUTE_NAME_TOKEN_RES, !StringUtils.isBlank(userDetails.getAvatar())? userDetails.getAvatar(): "");
            });
        }

        JwtClaimsSet claims = claimsBuilder.build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return  this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    private String generateToken(User userDetails, long tokenValidityInSeconds, boolean isRefreshToken) {

        String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity = now.plus(Duration.ofSeconds(tokenValidityInSeconds));

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(userDetails.getUsername())
                .claim(AUTHORITIES_KEY, authorities);

        if(!isRefreshToken) {
            claimsBuilder.claims(claims -> {
                claims.put(ID_ATTRIBUTE_NAME_TOKEN_RES, userDetails.getId());
                claims.put(EMAIL_ATTRIBUTE_NAME_TOKEN_RES, userDetails.getEmail());
                claims.put(AVATAR_ATTRIBUTE_NAME_TOKEN_RES, !StringUtils.isBlank(userDetails.getAvatar())? userDetails.getAvatar(): "");
            });
        }

        JwtClaimsSet claims = claimsBuilder.build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return  this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
