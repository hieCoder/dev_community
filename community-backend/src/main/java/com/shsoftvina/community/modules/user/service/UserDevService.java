package com.shsoftvina.community.modules.user.service;

import com.shsoftvina.community.modules.root.user.service.UserService;

public interface UserDevService extends UserService {

    String getUsernameByPost(Long postId);
    String getUsernameByComponent(Long componentId);
}
