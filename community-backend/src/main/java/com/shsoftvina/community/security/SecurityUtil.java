package com.shsoftvina.community.security;

import com.shsoftvina.community.config.SecurityUtils;

import static com.shsoftvina.community.security.AuthoritiesConstant.ROLE_SUPER_ADMIN;

public final class SecurityUtil {

    public static String getUsernameQuery(){
        return SecurityUtils.hasCurrentUserThisAuthority(ROLE_SUPER_ADMIN)
                ? null : SecurityUtils.getCurrentUserLogin();
    }

    public static String getUsernameCurrent(){
        return SecurityUtils.getCurrentUserLogin();
    }
}
