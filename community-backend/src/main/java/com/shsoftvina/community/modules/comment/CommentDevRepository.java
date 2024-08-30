package com.shsoftvina.community.modules.comment;

import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.modules.root.comment.CommentRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDevRepository extends CommentRepository {

    @Query("select p.id as eventId, count(c.eventId) as totalComment from Post p left join Comment c on c.eventId = p.id and c.eventType = 'POST' where p.id in :postIds group by p.id")
    List<CountCommentProjection> getListTotalCommentOfPost(List<Long> postIds);

    @Query("select count(c.id) from Comment c where c.eventId = :eventId and c.eventType = :commentType")
    long getTotalCommentOfEvent(Long eventId, ECommentType commentType);
}
