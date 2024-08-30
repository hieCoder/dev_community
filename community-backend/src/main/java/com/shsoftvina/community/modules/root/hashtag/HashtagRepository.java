package com.shsoftvina.community.modules.root.hashtag;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HashtagRepository extends JpaRepository<HashTag, Long> {

    @Query("select h from HashTag h join EventHashTag eh on h.id = eh.hashTagId join Post p on p.id = eh.eventId" +
            " where h.eventType = 'POST' and p.id = :postId")
    List<HashTag> findAllByPost(Long postId);

    @Query("select h from HashTag h join EventHashTag eh on h.id = eh.hashTagId join Component c on c.id = eh.eventId" +
            " where h.eventType = 'COMPONENT' and c.id = :componentId")
    List<HashTag> findAllByComponent(Long componentId);

    @Query("select h from HashTag h where h.eventType = :hashTagType and h.name in :names")
    List<HashTag> findAllNameIn(List<String> names, EHashTagType hashTagType);
}
