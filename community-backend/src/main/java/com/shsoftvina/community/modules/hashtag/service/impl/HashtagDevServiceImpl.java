package com.shsoftvina.community.modules.hashtag.service.impl;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.modules.hashtag.HashtagDevRepository;
import com.shsoftvina.community.modules.hashtag.mapper.HashTagResMapper;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.modules.hashtag.service.HashtagDevService;
import com.shsoftvina.community.modules.root.hashtag.service.impl.HashTagServiceImpl;
import com.shsoftvina.community.modules.root.hashtag.service.impl.HashtagEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HashtagDevServiceImpl extends HashTagServiceImpl implements HashtagDevService {

    @Autowired
    private HashtagDevRepository hashtagDevRepository;

    @Autowired
    private HashTagResMapper hashTagResMapper;

    @Override
    public List<HashTagRes> getListHashtagPostOutstanding() {
        return hashTagResMapper.toDto(hashtagDevRepository.getListHashtagPostOutstanding());
    }

    @Override
    public void saveAll(List<HashTag> hashTagList) {
        hashtagDevRepository.saveAll(hashTagList);
    }

    @Override
    public List<HashTagRes> getLisRelatedPostByFilterHashTag(Long id) {
        return hashTagResMapper.toDto(hashtagDevRepository.getLisRelatedPostByFilterHashTag(id));
    }
}
