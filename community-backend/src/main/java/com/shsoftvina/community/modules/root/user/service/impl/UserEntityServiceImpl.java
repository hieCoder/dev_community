package com.shsoftvina.community.modules.root.user.service.impl;

import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.root.user.UserRepository;
import com.shsoftvina.community.modules.root.user.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        log.debug("Save user to database with email: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.USER_NOT_FOUND));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.USER_NOT_FOUND));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsernameOAuth2(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return this.findByEmail(email).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.USER_NOT_FOUND));
    }

    @Override
    public User findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.USER_NOT_FOUND));
    }

    @Override
    public User findUserByPostId(Long postId) {
        return userRepository.findByPostId(postId).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.USER_NOT_FOUND));
    }
}
