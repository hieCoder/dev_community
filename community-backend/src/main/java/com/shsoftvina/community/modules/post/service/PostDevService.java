package com.shsoftvina.community.modules.post.service;

import com.shsoftvina.community.modules.home.outstading.PostOutstandingProjection;
import com.shsoftvina.community.modules.post.model.FilterPostByHashTagRes;
import com.shsoftvina.community.modules.post.model.OutstandingPostRes;
import com.shsoftvina.community.modules.post.model.PostDetailRes;
import com.shsoftvina.community.modules.post.model.PostRes;
import com.shsoftvina.community.modules.root.post.service.PostService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostDevService extends PostService {

    // home
    List<OutstandingPostRes> getListOutstandingHotTopic(Pageable pageable);
    List<OutstandingPostRes> getListOutstandingLatestAndGreatest(Pageable pageable);
    List<OutstandingPostRes> getListOutstandingNewAndNoteworthy(Pageable pageable);

    // home info
    List<PostOutstandingProjection> getListOutstandingPostInfo();

    // post
    List<PostRes> findAll();

    // post detail
    PostDetailRes findDetail(String ipClient, Long id);
    void updateReadingTime(Long id, Integer second);

    // click hashtag
    FilterPostByHashTagRes findAllByHashTagId(Long hashTagId);

    void updateSharing(Long id);
}