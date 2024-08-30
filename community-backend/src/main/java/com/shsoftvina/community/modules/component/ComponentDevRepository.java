package com.shsoftvina.community.modules.component;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.modules.home.outstading.ComponentOutstandingProjection;
import com.shsoftvina.community.modules.root.component.ComponentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComponentDevRepository extends ComponentRepository {

    @Query("select " +
        " co.id as id," +
        " co.title as title," +
        " coalesce(count(distinct c.id), 0) as commentCount," +
        " coalesce(count(distinct l.id), 0) as likeCount," +
        " coalesce(co.totalShare, 0) as shareCount," +
        " coalesce(co.totalView, 0) as viewCount " +
        " from Component co" +
        " left join Like l on co.id = l.eventId and l.eventType = 'COMPONENT'" +
        " left join Comment c on co.id = c.eventId and c.eventType = 'COMPONENT' and c.status = 'ACTIVATED'" +
        " where co.status = 'ACTIVATED'" +
        " group by co.id")
    List<ComponentOutstandingProjection> getComponentOutstanding();

    @Query("select c from Component c where c.status = 'ACTIVATED' and c.id in :ids")
    List<Component> findByIdIn(@Param("ids") List<Long> ids);
}
