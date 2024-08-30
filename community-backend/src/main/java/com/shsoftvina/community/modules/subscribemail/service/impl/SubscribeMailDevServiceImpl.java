package com.shsoftvina.community.modules.subscribemail.service.impl;

import com.shsoftvina.community.domain.SubscribeMail;
import com.shsoftvina.community.modules.root.subscribemail.service.impl.SubscribeMailServiceImpl;
import com.shsoftvina.community.modules.subscribemail.service.SubscribeMailDevService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubscribeMailDevServiceImpl extends SubscribeMailServiceImpl implements SubscribeMailDevService {

    @Override
    public void subscribeMail(String email) {
        if (super.findByEmail(email).isEmpty()) {
            SubscribeMail subscribeMail = new SubscribeMail();
            subscribeMail.setEmail(email);
            super.save(subscribeMail);
        }
    }
}
