package com.shsoftvina.community.modules.root.ip.service.impl;

import com.shsoftvina.community.domain.Ip;
import com.shsoftvina.community.modules.ip.IpDevRepository;
import com.shsoftvina.community.modules.root.ip.IpRepository;
import com.shsoftvina.community.modules.root.ip.service.IpEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class IpEntityServiceImpl implements IpEntityService {

    @Autowired
    private IpRepository ipRepository;

    @Override
    public Ip updateIp(String ipClient) {
        return ipRepository.findByIpClient(ipClient).orElseGet(() -> {
            Ip ip = new Ip();
            ip.setIpClient(ipClient);
            return ipRepository.save(ip);
        });
    }

    @Override
    public Ip updateIp(String ipClient, String nickName) {
        Ip ip = this.updateIp(ipClient);
        ip.setNickName(nickName);
        return ipRepository.save(ip);
    }

    @Override
    public Ip save(Ip ip) {
        return ipRepository.save(ip);
    }
}
