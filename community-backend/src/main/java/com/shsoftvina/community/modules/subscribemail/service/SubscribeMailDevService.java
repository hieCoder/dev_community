package com.shsoftvina.community.modules.subscribemail.service;

import com.shsoftvina.community.modules.root.subscribemail.service.SubscribeMailService;

public interface SubscribeMailDevService extends SubscribeMailService {
    void subscribeMail(String email);
}
