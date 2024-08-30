package com.shsoftvina.community.modules.root.subscribemail.service.impl;

import com.shsoftvina.community.domain.SubscribeMail;
import com.shsoftvina.community.modules.root.subscribemail.service.SubscribeMailEntityService;
import com.shsoftvina.community.modules.root.subscribemail.SubscribeMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubscribeMailEntityServiceImpl implements SubscribeMailEntityService {

    @Autowired
    private SubscribeMailRepository subscribeMailRepository;

    @Override
    public List<SubscribeMail> findAll() {
        return subscribeMailRepository.findAll();
    }

    @Override
    public Optional<SubscribeMail> findByEmail(String email) {
        return subscribeMailRepository.findByEmail(email);
    }
}
