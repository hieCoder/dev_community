package com.shsoftvina.community.modules.like.service.impl;

import com.shsoftvina.community.domain.Ip;
import com.shsoftvina.community.domain.Like;
import com.shsoftvina.community.domain.enumration.ELikeType;
import com.shsoftvina.community.modules.group_noti.service.GroupNotiDevService;
import com.shsoftvina.community.modules.ip.service.IpDevService;
import com.shsoftvina.community.modules.like.LikeDevRepository;
import com.shsoftvina.community.modules.like.service.LikeDevService;
import com.shsoftvina.community.modules.notification.service.NotificationDevService;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.modules.user.service.UserDevService;
import com.shsoftvina.community.security.SecurityUtil;
import com.shsoftvina.community.config.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeDevServiceImpl implements LikeDevService {

    @Autowired
    private LikeDevRepository likeDevRepository;

    @Autowired
    private IpDevService ipDevService;

    @Autowired
    private NotificationDevService notificationDevService;

    @Autowired
    private GroupNotiDevService groupNotiDevService;

    @Autowired
    private UserDevService userDevService;

    @Override
    public List<String> getListIpClientLiked(Long eventId, ELikeType likeType) {
        return likeDevRepository.findAllByEvent(eventId, likeType).stream()
                .map(l -> l.getUser() != null ? l.getUser().getUsername() : l.getIp().getIpClient()).toList();
    }

    @Override
    public void likeAction(Long eventId, ELikeType likeType, String ipClient) {

        if(SecurityUtils.isAuthenticated()){
            String username = SecurityUtil.getUsernameCurrent();
            likeDevRepository.findByEventAndUser(eventId, likeType, username).ifPresentOrElse(
                    like -> {
                        likeDevRepository.deleteById(like.getId());
                    },
                    () -> {
                        Like likeEntity = new Like();
                        likeEntity.setEventId(eventId);
                        likeEntity.setEventType(likeType);
                        likeEntity.setUser(userDevService.findByUsername(username));
                        likeEntity = likeDevRepository.save(likeEntity);

                        this.handlePushNotification(likeEntity);
                    }
            );
        } else {
            Ip ip = ipDevService.updateIp(ipClient);

            likeDevRepository.findByEvent(eventId, likeType, ipClient).ifPresentOrElse(
                    like -> {
                        likeDevRepository.deleteById(like.getId());
                    },
                    () -> {
                        Like likeEntity = new Like();
                        likeEntity.setEventId(eventId);
                        likeEntity.setEventType(likeType);
                        likeEntity.setIp(ip);
                        likeEntity = likeDevRepository.save(likeEntity);

                        this.handlePushNotification(likeEntity);
                    }
            );
        }
    }

    @Async
    public void handlePushNotification(Like like) {

        String username = null;
        if(like.getEventType().equals(ELikeType.POST)){
            username = userDevService.getUsernameByPost(like.getEventId());
        } else if(like.getEventType().equals(ELikeType.COMPONENT)){
            username = userDevService.getUsernameByComponent(like.getEventId());
        }

        if (groupNotiDevService.isUserHasGroupSocical(username)) {
            notificationDevService.createNotification(CreateNotificationReq.buildForLike(like));
        }
    }
}

