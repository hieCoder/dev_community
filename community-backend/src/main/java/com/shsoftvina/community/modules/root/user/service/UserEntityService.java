package com.shsoftvina.community.modules.root.user.service;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.User;

import java.util.Optional;

public interface UserEntityService {

    User save(User user);
    User findByUsername(String username);
    User findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOAuth2(String username);
    User getByEmail(String email);
    User findByRefreshToken(String refreshToken);
    User findUserByPostId(Long postId);
}
