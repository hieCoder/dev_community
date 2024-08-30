package com.shsoftvina.community.modules.root.comment.service.impl;

import com.shsoftvina.community.domain.Comment;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.comment.CommentDevRepository;
import com.shsoftvina.community.modules.root.comment.CommentRepository;
import com.shsoftvina.community.modules.root.comment.service.CommentEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentEntityServiceImpl implements CommentEntityService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.COMMENT_NOT_FOUND));
    }
}
