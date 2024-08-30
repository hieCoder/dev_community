package com.shsoftvina.community.modules.root.event_hashtag.service;

import com.shsoftvina.community.domain.enumration.EHashTagType;

import java.util.List;

public interface EventHashTagService extends EventHashTagEntityService{

    void addEventHashTags(Long eventId, List<Long> hashTagIds);
    void deleteEventHashTags(List<Long> eventIds, EHashTagType hashTagType);
}
