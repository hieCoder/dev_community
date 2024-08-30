package com.shsoftvina.community.modules.user.service.impl;

import com.shsoftvina.community.modules.root.user.service.impl.UserServiceImpl;
import com.shsoftvina.community.modules.user.UserDevRepository;
import com.shsoftvina.community.modules.user.service.UserDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDevServiceImpl extends UserServiceImpl implements UserDevService {

    @Autowired
    private UserDevRepository userDevRepository;

    @Override
    public String getUsernameByPost(Long postId) {
        return userDevRepository.findByPost(postId).get().getUsername();
    }

    @Override
    public String getUsernameByComponent(Long componentId) {
        return userDevRepository.findByComponent(componentId).get().getUsername();
    }
}
