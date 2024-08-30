package com.shsoftvina.community.modules.root.ip;

import com.shsoftvina.community.domain.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IpRepository extends JpaRepository<Ip, Long> {

    @Query("select i from Ip i where i.ipClient = :ipClient")
    Optional<Ip> findByIpClient(String ipClient);
}
