package com.shsoftvina.community.modules.ip.service.impl;

import com.shsoftvina.community.modules.ip.IpDevRepository;
import com.shsoftvina.community.modules.ip.service.IpDevService;
import com.shsoftvina.community.modules.root.ip.service.impl.IpEntityServiceImpl;
import com.shsoftvina.community.modules.root.ip.service.impl.IpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpDevServiceImpl extends IpServiceImpl implements IpDevService {

    @Autowired
    private IpDevRepository ipDevRepository;

    @Override
    public long countAllIp() {
        return ipDevRepository.countAllIp();
    }
}
