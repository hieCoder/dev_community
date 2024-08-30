package com.shsoftvina.community.modules.root.post.service.impl;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.post.PostDevRepository;
import com.shsoftvina.community.modules.root.post.PostRepository;
import com.shsoftvina.community.modules.root.post.service.PostEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostEntityServiceImpl implements PostEntityService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post findById(Long id, List<EStatus> statuses) {
        return postRepository.findById(id, statuses).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.POST_NOT_FOUND));
    }

    @Override
    public List<Post> findAll(String keyword) {
        return postRepository.findAll(keyword);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.POST_NOT_FOUND));
    }
}
