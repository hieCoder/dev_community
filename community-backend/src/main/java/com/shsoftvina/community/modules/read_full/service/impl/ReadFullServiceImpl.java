package com.shsoftvina.community.modules.read_full.service.impl;

import com.shsoftvina.community.domain.Ip;
import com.shsoftvina.community.domain.ReadFull;
import com.shsoftvina.community.modules.ip.service.IpDevService;
import com.shsoftvina.community.modules.read_full.ReadFullRepository;
import com.shsoftvina.community.modules.read_full.service.ReadFullService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReadFullServiceImpl implements ReadFullService {

    @Autowired
    private IpDevService ipDevService;

    @Autowired
    private ReadFullRepository readFullRepository;

    @Override
    public void updateReadFull(Long postId, String ipClient) {

        Ip ip = ipDevService.updateIp(ipClient);
        Long ipId = ip.getId();

        Optional<ReadFull> readFullOptional = readFullRepository.findByIpClient(postId, ipId);
        ReadFull readFull = null;
        if(readFullOptional.isPresent()){
            readFull = readFullOptional.get();
        } else {
            readFull = ReadFull.builder()
                    .postId(postId)
                    .ipId(ipId).build();
        }
        readFull.setIsReadFull(true);

        readFullRepository.save(readFull);
    }
}
