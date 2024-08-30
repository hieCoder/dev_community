package com.shsoftvina.community.modules.root.user.service;

public interface UserService extends UserEntityService{

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
