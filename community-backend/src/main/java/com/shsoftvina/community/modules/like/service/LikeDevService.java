package com.shsoftvina.community.modules.like.service;

import com.shsoftvina.community.domain.enumration.ELikeType;

import java.util.List;

public interface LikeDevService {

    List<String> getListIpClientLiked(Long eventId, ELikeType likeType);
    void likeAction(Long eventId, ELikeType likeType, String ipClient);
}
