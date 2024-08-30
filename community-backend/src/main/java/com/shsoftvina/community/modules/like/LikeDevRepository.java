package com.shsoftvina.community.modules.like;

import com.shsoftvina.community.domain.Like;
import com.shsoftvina.community.domain.enumration.ELikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeDevRepository extends JpaRepository<Like, Long> {

    @Query("select l from Like l where l.eventId = :eventId and l.eventType = :likeType")
    List<Like> findAllByEvent(Long eventId, ELikeType likeType);

    @Query("select l from Like l where l.eventId = :eventId and l.eventType = :likeType and l.ip.ipClient = :ipClient")
    Optional<Like> findByEvent(Long eventId, ELikeType likeType, String ipClient);

    @Query("select l from Like l where l.eventId = :eventId and l.eventType = :likeType and l.user.username = :username")
    Optional<Like> findByEventAndUser(Long eventId, ELikeType likeType, String username);
}
