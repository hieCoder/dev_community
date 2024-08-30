package com.shsoftvina.community.modules.comment.service;

import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.modules.comment.CountCommentProjection;
import com.shsoftvina.community.modules.comment.model.CommentDevRes;
import com.shsoftvina.community.modules.comment.model.CreateCommentDevReq;
import com.shsoftvina.community.modules.root.comment.service.CommentEntityService;
import com.shsoftvina.community.modules.root.comment.service.CommentService;

import java.util.List;

public interface CommentDevService extends CommentService {

    List<CountCommentProjection> getListTotalCommentOfPost(List<Long> postIds);
    long getTotalCommentOfEvent(Long evenId, ECommentType commentType);
    List<CommentDevRes> findAllByEvent(Long eventId, ECommentType commentType);
    CommentDevRes createComment(String ipClient, CreateCommentDevReq req);
}
