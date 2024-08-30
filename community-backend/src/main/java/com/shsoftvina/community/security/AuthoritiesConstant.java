package com.shsoftvina.community.security;

import com.shsoftvina.community.domain.enumration.ERole;

public final class AuthoritiesConstant {

    public static final String ROLE_PREFIX = "ROLE_";

    public static final String ADMIN = ERole.ADMIN.name();
    public static final String SUPER_ADMIN = ERole.SUPER_ADMIN.name();

    public static final String ROLE_ADMIN = ROLE_PREFIX + ADMIN;
    public static final String ROLE_SUPER_ADMIN = ROLE_PREFIX + SUPER_ADMIN;
    public static final String ROLE_ANONYMOUS = ROLE_PREFIX + "ANONYMOUS";
}
