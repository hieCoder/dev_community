package com.shsoftvina.community.management;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.enumration.EActionNotification;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.root.group_noti.service.GroupNotiService;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.modules.root.notification.service.NotificationService;
import com.shsoftvina.community.modules.root.post.service.PostService;
import com.shsoftvina.community.modules.root.user.service.UserService;
import com.shsoftvina.community.service.MailService;
import com.shsoftvina.community.service.ScheduleService;
import com.shsoftvina.community.utils.CronUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class SchedulingManagement {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PostService postService;

    @Autowired
    private MailService mailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GroupNotiService groupNotiService;

    @Autowired
    private UserService userService;

    public void createPostScheduling(Post post, Instant postingTime) {
        scheduleService.startSchedule(() -> {
            post.setStatus(EStatus.ACTIVATED);
            postService.save(post);

            this.handlePushNotificationCreatePost(post);

            mailService.handSendMailTop5PostLatest(post, LocaleContextHolder.getLocale());
        }, CronUtil.convertInstantToCron(postingTime), post.getId().toString());
    }

    private void handlePushNotificationCreatePost(Post post) {

        if (groupNotiService.isUserHasGroupAccountActivity(post.getUser().getUsername())) {
            notificationService.createNotification(CreateNotificationReq.buildForPost(post, EActionNotification.CREATE_POST, false));
        }
    }

    public void stopPostScheduling(Post post) {
        scheduleService.stopSchedule(post.getId().toString());
    }

    public void replaceNewPostScheduling(Post post, Instant newPostingTime) {
        this.stopPostScheduling(post);
        this.editPostScheduling(post, newPostingTime);
    }

    public void editPostScheduling(Post post, Instant postingTime) {
        scheduleService.startSchedule(() -> {
            post.setStatus(EStatus.ACTIVATED);
            postService.save(post);

            this.handlePushNotificationEditPost(post);
        }, CronUtil.convertInstantToCron(postingTime), post.getId().toString());
    }

    private void handlePushNotificationEditPost(Post post) {
        if (groupNotiService.isUserHasGroupAccountActivity(userService.findUserByPostId(post.getId()).getUsername())) { System.out.println(789);
            notificationService.createNotification(CreateNotificationReq.buildForPost(post, EActionNotification.UPDATE_POST, false));
        }
    }
}
