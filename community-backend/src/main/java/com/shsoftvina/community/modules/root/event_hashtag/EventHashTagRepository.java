package com.shsoftvina.community.modules.root.event_hashtag;

import com.shsoftvina.community.domain.EventHashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventHashTagRepository extends JpaRepository<EventHashTag, Long> {

    @Query("select e from EventHashTag e join HashTag h on e.hashTagId = h.id and h.eventType = :hashTagType" +
            " where e.eventId in :eventIds")
    List<EventHashTag> findAll(List<Long> eventIds, EHashTagType hashTagType);

    @Transactional
    @Modifying
    @Query("delete from EventHashTag e where e.id in :ids")
    void deleteByIdIn(List<Long> ids);
}
