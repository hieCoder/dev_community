package com.shsoftvina.community.modules.root.notification.service.impl;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.domain.Notification;
import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.domain.enumration.ENotificationType;
import com.shsoftvina.community.modules.root.component.service.ComponentService;
import com.shsoftvina.community.modules.root.group_noti.service.GroupNotiService;
import com.shsoftvina.community.modules.root.notification.NotificationRepository;
import com.shsoftvina.community.modules.root.notification.mapper.CreateNotificationReqMapper;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.modules.root.notification.service.NotificationService;
import com.shsoftvina.community.modules.root.post.service.PostService;
import com.shsoftvina.community.modules.root.user.service.UserService;
import com.shsoftvina.community.security.SecurityUtil;
import com.shsoftvina.community.service.SocketService;
import com.shsoftvina.community.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shsoftvina.community.domain.enumration.EActionNotification.*;
import static com.shsoftvina.community.domain.enumration.ENotificationType.COMPONENT;
import static com.shsoftvina.community.domain.enumration.ENotificationType.POST;

@Service
@Transactional
@Primary
public class NotificationServiceImpl extends NotificationEntityServiceImpl implements NotificationService {

    @Autowired
    private CreateNotificationReqMapper createNotificationReqMapper;

    @Autowired
    private GroupNotiService groupNotiService;

    @Autowired
    @Lazy
    private PostService postService;

    @Autowired
    @Lazy
    private ComponentService componentService;

    @Autowired
    private SocketService socketService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationRepository notificationRepository;

    private boolean isActionComment(CreateNotificationReq req){
        return req.getAction().equals(NEW_AND_REPLY_COMMENT_POST) || req.getAction().equals(NEW_AND_REPLY_COMMENT_COMPONENT);
    }

    private boolean isActionLike(CreateNotificationReq req){
        return req.getAction().equals(POST_LIKED) || req.getAction().equals(COMPONENT_LIKED);
    }

    private boolean isActionDelete(CreateNotificationReq req){
        return req.getAction().equals(DELETE_POST) || req.getAction().equals(DELETE_COMPONENT);
    }

    @Override
    public void createNotification(CreateNotificationReq req) {

        Notification notification = createNotificationReqMapper.toEntity(req);
        notification.setGroup(groupNotiService.findByCode(req.getGroupCode()));

        User user = null;
        Map<String, Object> metadataMap = req.getMetadataMap();

        if(req.getNotificationType().equals(POST)){

            Post post = postService.findById(req.getEventId());
            user = post.getUser();

            if(user.getUsername().equals(SecurityUtil.getUsernameCurrent()) && this.isActionComment(req)) return;

            if(user.getUsername().equals(SecurityUtil.getUsernameCurrent()) && this.isActionLike(req)) return;

            if(req.isNotiForSocial()) {
                if (req.isNotiForLike()) {
                    metadataMap.put("message", post.getTitle());
                }
            }
        } else if (req.getNotificationType().equals(COMPONENT)){

            Component component = componentService.findById(req.getEventId());
            user = component.getUser();

            if(user.getUsername().equals(SecurityUtil.getUsernameCurrent()) && this.isActionComment(req)) return;

            if(req.isNotiForSocial()) {

                if(req.isNotiForLike()){
                    metadataMap.put("message", component.getTitle());
                }
            }
        } else {
            user = userService.findById(req.getEventId());
        }
        notification.setUser(user);
        notification.setMetadata(JsonUtils.objectToJson(metadataMap));

        notification = super.save(notification);

        if(isActionDelete(req)){
            handleNotiForActionDelete(req);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", notification.getId());
        map.put("href", notification.getHref());
        map.put("action", notification.getAction());
        map.put("message", notification.getAction().getMessage());
        map.put( "metadata", JsonUtils.jsonToObject(notification.getMetadata(), Object.class));
        map.put("createdDate", notification.getCreatedDate());
        socketService.pushWSToClientNotificationIcon(user.getId(), map);
    }

    private void handleNotiForActionDelete(CreateNotificationReq req){
        Long eventId = req.getEventId();
        ENotificationType notificationType = req.getNotificationType();

        List<Notification> notificationList = notificationRepository.findAllInEvent(eventId, notificationType);
        notificationList.forEach(n -> n.setHref(null));
        notificationRepository.saveAll(notificationList);
    }
}
