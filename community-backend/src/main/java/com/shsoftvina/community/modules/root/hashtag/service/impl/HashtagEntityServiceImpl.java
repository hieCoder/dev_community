package com.shsoftvina.community.modules.root.hashtag.service.impl;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.modules.hashtag.HashtagDevRepository;
import com.shsoftvina.community.modules.root.hashtag.HashtagRepository;
import com.shsoftvina.community.modules.root.hashtag.service.HashTagEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HashtagEntityServiceImpl implements HashTagEntityService {

    @Autowired
    private HashtagRepository hashtagRepository;

    @Override
    public List<HashTag> getListHashTagOfPost(Long postId) {
        return hashtagRepository.findAllByPost(postId);
    }

    @Override
    public List<HashTag> getListHashTagOfComponent(Long componentId) {
        return hashtagRepository.findAllByComponent(componentId);
    }
}
