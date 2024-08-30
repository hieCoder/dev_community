package com.shsoftvina.community.modules.root.ip.service;

import com.shsoftvina.community.domain.Ip;

public interface IpEntityService {
    Ip updateIp(String ipClient);
    Ip updateIp(String ipClient, String nickName);
    Ip save(Ip ip);
}
