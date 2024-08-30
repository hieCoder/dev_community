package com.shsoftvina.community.modules.hashtag;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.modules.root.hashtag.HashtagRepository;
import org.springframework.beans.PropertyValues;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HashtagDevRepository extends HashtagRepository {

    @Query("select distinct h from HashTag h join EventHashTag eh on h.id = eh.hashTagId join Post p on p.id = eh.eventId" +
            " where h.eventType = 'POST' and p.status = 'ACTIVATED'" +
            " order by h.countRead desc")
    List<HashTag> getListHashtagPostOutstanding();

    @Query("select distinct h from HashTag h" +
                " join EventHashTag eh on h.id = eh.hashTagId and h.eventType = 'POST'" +
                " join Post p on eh.eventId = p.id " +
            " where h.id <> :id and p.id in (select p2.id from Post p2 join EventHashTag eh2 on p2.id = eh2.eventId where eh2.hashTagId = :id)")
    List<HashTag> getLisRelatedPostByFilterHashTag(Long id);
}
