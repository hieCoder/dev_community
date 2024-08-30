package com.shsoftvina.community.modules.root.post.service.impl;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.enumration.EActionNotification;
import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.root.comment.service.CommentService;
import com.shsoftvina.community.modules.root.group_noti.service.GroupNotiService;
import com.shsoftvina.community.modules.root.hashtag.service.HashTagService;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.modules.root.notification.service.NotificationService;
import com.shsoftvina.community.modules.root.post.PostRepository;
import com.shsoftvina.community.modules.root.post.service.PostService;
import com.shsoftvina.community.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Primary
@Slf4j
public class PostServiceImpl extends PostEntityServiceImpl implements PostService {

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GroupNotiService groupNotiService;

    protected void deletePosts(List<Long> ids) {

        List<Post> posts = postRepository.findByIdInNoDelete(ids);

        if(groupNotiService.isUserHasGroupAccountActivity(SecurityUtil.getUsernameCurrent())){
            this.pushNotification(posts);
        }

        posts.forEach(post -> post.setStatus(EStatus.DELETED));
        postRepository.saveAll(posts);

        log.debug("Delete comments of post ids = {}", ids);
        commentService.deleteComments(ids, ECommentType.POST);

        log.debug("Delete hashtags of post ids = {}", ids);
        hashTagService.deleteHashTagList(ids, EHashTagType.POST);
    }

    private void pushNotification(List<Post> posts) {
        posts.stream().filter(p -> p.getStatus().equals(EStatus.ACTIVATED)).forEach(p -> {
            log.debug("Push notification to post id = {}", p.getId());
            CreateNotificationReq req = CreateNotificationReq.buildForPost(p, EActionNotification.DELETE_POST, true);
            notificationService.createNotification(req);
        });
    }

    @Override
    public void saveAll(List<Post> posts) {
        postRepository.saveAll(posts);
    }
}
