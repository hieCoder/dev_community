package com.shsoftvina.community.modules.root.hashtag.service.impl;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.modules.hashtag.mapper.HashTagResMapper;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.modules.root.event_hashtag.service.EventHashTagService;
import com.shsoftvina.community.modules.root.hashtag.HashtagRepository;
import com.shsoftvina.community.modules.root.hashtag.service.HashTagService;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Primary
public class HashTagServiceImpl extends HashtagEntityServiceImpl implements HashTagService {

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private EventHashTagService eventHashTagService;

    @Autowired
    private HashTagResMapper hashTagResMapper;

    @Override
    public void updateHashTagListOfPost(List<String> hashTagList, Long postId) {
        this.updateHashTagList(hashTagList, postId, EHashTagType.POST);
    }

    @Override
    public void updateHashTagListOfConponent(List<String> hashTagList, Long componentId) {
        this.updateHashTagList(hashTagList, componentId, EHashTagType.COMPONENT);
    }

    @Override
    public void deleteHashTagList(List<Long> eventIds, EHashTagType hashTagType) {
        eventHashTagService.deleteEventHashTags(eventIds, hashTagType);
    }

    @Override
    public void deleteHashTagList(Long eventId, EHashTagType hashTagType) {
        eventHashTagService.deleteEventHashTags(List.of(eventId), hashTagType);
    }

    private static String removeAccents(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private void updateHashTagList(List<String> hashTagList, Long id, EHashTagType hashTagType) {

        if(hashTagList != null && !hashTagList.isEmpty()){
            List<String> lowerCaseHashTagList = hashTagList.stream()
                    .map(String::trim)
                    .map(HashTagServiceImpl::removeAccents)
                    .map(String::toLowerCase)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> hashTagListTemp = new ArrayList<>(lowerCaseHashTagList);

            List<String> lowerCaseHashTags = hashtagRepository.findAllNameIn(lowerCaseHashTagList, hashTagType).stream()
                    .map(HashTag::getName)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            lowerCaseHashTagList.removeAll(lowerCaseHashTags);

            Function<String, String> capitalize = tag -> {
                if (tag == null || tag.isEmpty()) {
                    return tag;
                }
                return tag.substring(0, 1).toUpperCase() + tag.substring(1).toLowerCase();
            };

            List<HashTag> newHashTags = lowerCaseHashTagList.stream()
                    .filter(tag -> !lowerCaseHashTags.contains(tag))
                    .map(tag -> HashTag.builder()
                            .name(capitalize.apply(tag))
                            .eventType(hashTagType)
                            .countRead(0)
                            .countReadInWeek(0).build())
                    .toList();
            hashtagRepository.saveAll(newHashTags);

            this.addEventHashTagList(hashTagListTemp, hashTagType, id);
        }
    }

    @Override
    public List<HashTagRes> getListHashTagResOfPost(Long postId) {
        return hashTagResMapper.toDto(super.getListHashTagOfPost(postId));
    }

    @Override
    public List<HashTagRes> getListHashTagResOfComponent(Long componentId) {
        return hashTagResMapper.toDto(super.getListHashTagOfComponent(componentId));
    }

    private void addEventHashTagList(List<String> hashTagList, EHashTagType hashTagType, Long eventId){
        List<Long> hashTagIds = hashtagRepository.findAllNameIn(hashTagList, hashTagType).stream()
                .map(HashTag::getId).toList();
        eventHashTagService.addEventHashTags(eventId, hashTagIds);
    }

    @Override
    public Map<Long, List<HashTagRes>> getMapHashTagOfPost(List<Long> postIds) {
        return postIds.stream().collect(Collectors.toMap(
                postId -> postId,
                postId -> this.getListHashTagResOfPost(postId)
        ));
    }

    @Override
    public Map<Long, List<HashTagRes>> getMapHashTagOfComponent(List<Long> componentIds) {
        return componentIds.stream().collect(Collectors.toMap(
                componentid -> componentid,
                componentid -> this.getListHashTagResOfComponent(componentid)
        ));
    }
}
