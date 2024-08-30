package com.shsoftvina.community.modules.ip;

import com.shsoftvina.community.modules.root.ip.IpRepository;
import org.springframework.data.jpa.repository.Query;

public interface IpDevRepository extends IpRepository {

    @Query("select count(1) from Ip")
    long countAllIp();
}
