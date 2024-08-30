package com.shsoftvina.community.modules.read_full;

import com.shsoftvina.community.domain.ReadFull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReadFullRepository extends JpaRepository<ReadFull, Long> {

    @Query("select r from ReadFull r where r.postId = :postId and r.ipId = :ipId")
    Optional<ReadFull> findByIpClient(Long postId, Long ipId);
}
