package com.shsoftvina.community.modules.root.comment.service.impl;

import com.shsoftvina.community.domain.Comment;
import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.comment.CommentDevRepository;
import com.shsoftvina.community.modules.root.comment.CommentRepository;
import com.shsoftvina.community.modules.root.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Primary
public class CommentServiceImpl extends CommentEntityServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Async
    @Override
    public void deleteComments(List<Long> eventIds, ECommentType commentType) {
        List<Comment> comments = commentRepository.findAllByEvent(eventIds, commentType);
        comments.forEach(comment -> comment.setStatus(EStatus.DELETED));
        commentRepository.saveAll(comments);
    }

    @Async
    @Override
    public void deleteComments(Long eventId, ECommentType commentType) {
        List<Comment> comments = commentRepository.findAllByEvent(eventId, commentType);
        comments.forEach(comment -> comment.setStatus(EStatus.DELETED));
        commentRepository.saveAll(comments);
    }
}
