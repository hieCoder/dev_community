package com.shsoftvina.community.modules.root.hashtag.service;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;

import java.util.List;

public interface HashTagEntityService {
    List<HashTag> getListHashTagOfPost(Long postId);
    List<HashTag> getListHashTagOfComponent(Long componentId);
}
