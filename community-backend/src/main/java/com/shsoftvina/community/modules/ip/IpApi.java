package com.shsoftvina.community.modules.ip;

import com.shsoftvina.community.modules.ip.service.IpDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ips")
public class IpApi {

    @Autowired
    private IpDevService ipDevService;

    @PostMapping
    public ResponseEntity<Void> updateIp(@RequestHeader String ipClient) {
        ipDevService.updateIp(ipClient);
        return ResponseEntity.noContent().build();
    }
}
