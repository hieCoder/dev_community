package com.shsoftvina.community.modules.root.hashtag.service;

import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;

import java.util.List;
import java.util.Map;

public interface HashTagService extends HashTagEntityService{
    void updateHashTagListOfPost(List<String> hashTagList, Long postId);
    void updateHashTagListOfConponent(List<String> hashTagList, Long componentId);
    void deleteHashTagList(List<Long> eventIds, EHashTagType hashTagType);
    void deleteHashTagList(Long eventId, EHashTagType hashTagType);
    List<HashTagRes> getListHashTagResOfPost(Long eventId);
    List<HashTagRes> getListHashTagResOfComponent(Long eventId);
    Map<Long, List<HashTagRes>> getMapHashTagOfPost(List<Long> postIds);
    Map<Long, List<HashTagRes>> getMapHashTagOfComponent(List<Long> componentIds);
}
