package com.shsoftvina.community.modules.root.comment.service;

import com.shsoftvina.community.domain.enumration.ECommentType;

import java.util.List;

public interface CommentService extends CommentEntityService {

    void deleteComments(List<Long> eventIds, ECommentType commentType);
    void deleteComments(Long eventId, ECommentType commentType);
}
