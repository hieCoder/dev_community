package com.shsoftvina.community.modules.root.comment;

import com.shsoftvina.community.domain.Comment;
import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.modules.comment.CountCommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.id = :id")
    Optional<Comment> findById(Long id);

    @Query("select c from Comment c where c.eventId in :eventIds and c.eventType = :commentType order by c.createdDate desc")
    List<Comment> findAllByEvent(List<Long> eventIds, ECommentType commentType);

    @Query("select c from Comment c where c.eventId = :eventId and c.eventType = :commentType order by c.createdDate desc")
    List<Comment> findAllByEvent(Long eventId, ECommentType commentType);
}
