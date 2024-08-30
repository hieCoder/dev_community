package com.shsoftvina.community.modules.root.subscribemail.service.impl;

import com.shsoftvina.community.domain.SubscribeMail;
import com.shsoftvina.community.modules.root.subscribemail.SubscribeMailRepository;
import com.shsoftvina.community.modules.root.subscribemail.service.SubscribeMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Primary
public class SubscribeMailServiceImpl extends SubscribeMailEntityServiceImpl implements SubscribeMailService {

    @Autowired
    private SubscribeMailRepository subscribeMailRepository;

    protected void save(SubscribeMail subscribeMail){
        subscribeMailRepository.save(subscribeMail);
    }
}
