package com.shsoftvina.community.modules.hashtag.service;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.modules.root.hashtag.service.HashTagService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HashtagDevService extends HashTagService {
    List<HashTagRes> getListHashtagPostOutstanding();
    void saveAll(List<HashTag> hashTagList);
    List<HashTagRes> getLisRelatedPostByFilterHashTag(Long id);
}
