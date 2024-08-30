package com.shsoftvina.community.modules.root.subscribemail.service;

import com.shsoftvina.community.domain.SubscribeMail;

import java.util.List;
import java.util.Optional;

public interface SubscribeMailEntityService {

    List<SubscribeMail> findAll();
    Optional<SubscribeMail> findByEmail(String email);
}
