package com.shsoftvina.community.modules.root.event_hashtag.service.impl;

import com.shsoftvina.community.domain.EventHashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.modules.root.event_hashtag.EventHashTagRepository;
import com.shsoftvina.community.modules.root.event_hashtag.service.EventHashTagService;
import com.shsoftvina.community.modules.root.hashtag.service.HashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventHashTagServiceImpl extends EventHashTagEntityServiceImpl implements EventHashTagService {

    @Autowired
    private EventHashTagRepository eventHashTagRepository;

    @Override
    public void addEventHashTags(Long eventId, List<Long> hashTagIds) {
        List<EventHashTag> entities = hashTagIds.stream()
                .map(hashTagId -> {
                    EventHashTag entity = new EventHashTag();
                    entity.setEventId(eventId);
                    entity.setHashTagId(hashTagId);
                    return entity;
                })
                .toList();
        eventHashTagRepository.saveAll(entities);
    }

    @Override
    public void deleteEventHashTags(List<Long> eventIds, EHashTagType hashTagType) {
        List<Long> ids = eventHashTagRepository.findAll(eventIds, hashTagType).stream()
                .map(EventHashTag::getId).toList();
        eventHashTagRepository.deleteByIdIn(ids);
    }
}
