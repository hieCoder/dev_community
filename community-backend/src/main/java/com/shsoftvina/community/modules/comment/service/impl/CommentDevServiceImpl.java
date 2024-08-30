package com.shsoftvina.community.modules.comment.service.impl;

import com.shsoftvina.community.domain.Comment;
import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.comment.CommentDevRepository;
import com.shsoftvina.community.modules.comment.CountCommentProjection;
import com.shsoftvina.community.modules.comment.mapper.CommentResMapper;
import com.shsoftvina.community.modules.comment.mapper.CreateCommentReqMapper;
import com.shsoftvina.community.modules.comment.model.CommentDevRes;
import com.shsoftvina.community.modules.comment.model.CreateCommentDevReq;
import com.shsoftvina.community.modules.comment.service.CommentDevService;
import com.shsoftvina.community.modules.group_noti.service.GroupNotiDevService;
import com.shsoftvina.community.modules.ip.service.IpDevService;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.modules.notification.service.NotificationDevService;
import com.shsoftvina.community.modules.root.comment.service.impl.CommentServiceImpl;
import com.shsoftvina.community.modules.user.service.UserDevService;
import com.shsoftvina.community.security.SecurityUtil;
import com.shsoftvina.community.config.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentDevServiceImpl extends CommentServiceImpl implements CommentDevService {

    @Autowired
    private CommentDevRepository commentDevRepository;

    @Autowired
    private CommentResMapper commentResMapper;

    @Autowired
    private CreateCommentReqMapper createCommentReqMapper;

    @Autowired
    private IpDevService ipDevService;

    @Autowired
    private NotificationDevService notificationDevService;

    @Autowired
    private GroupNotiDevService groupNotiDevService;

    @Autowired
    private UserDevService userDevService;

    @Override
    public List<CountCommentProjection> getListTotalCommentOfPost(List<Long> postIds) {
        return commentDevRepository.getListTotalCommentOfPost(postIds);
    }

    @Override
    public long getTotalCommentOfEvent(Long evenId, ECommentType commentType) {
        return commentDevRepository.getTotalCommentOfEvent(evenId, commentType);
    }

    @Override
    public List<CommentDevRes> findAllByEvent(Long eventId, ECommentType commentType) {
        return commentResMapper.toDto(commentDevRepository.findAllByEvent(eventId, commentType));
    }

    @Override
    public CommentDevRes createComment(String ipClient, CreateCommentDevReq req) {

        Long parentId = req.getParentId();

        Comment comment = createCommentReqMapper.toEntity(req);
        comment.setStatus(EStatus.ACTIVATED);
        if(parentId != null){
            comment.setParent(super.findById(parentId));
        }

        if(SecurityUtils.isAuthenticated()){
            comment.setUser(userDevService.findByUsername(SecurityUtil.getUsernameCurrent()));
        } else {
            if(parentId == null && req.getIsSaveNickName()){
                ipDevService.updateIp(ipClient, req.getNickName());
            }
        }

        comment = commentDevRepository.save(comment);

        this.handlePushNotification(comment);

        return commentResMapper.toDto(comment);
    }

    @Async
    public void handlePushNotification(Comment comment) {

        String username = null;
        if(comment.getEventType().equals(ECommentType.POST)){
            username = userDevService.getUsernameByPost(comment.getEventId());
        } else if(comment.getEventType().equals(ECommentType.COMPONENT)){
            username = userDevService.getUsernameByComponent(comment.getEventId());
        }

        if(groupNotiDevService.isUserHasGroupSocical(username)){
            notificationDevService.createNotification(CreateNotificationReq.buildForComment(comment));
        }
    }
}