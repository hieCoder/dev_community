package com.shsoftvina.community.modules.root.component.service.impl;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.domain.Example;
import com.shsoftvina.community.domain.enumration.*;
import com.shsoftvina.community.modules.comment.service.CommentDevService;
import com.shsoftvina.community.modules.post.service.PostDevService;
import com.shsoftvina.community.modules.root.comment.service.CommentService;
import com.shsoftvina.community.modules.root.component.service.ComponentService;
import com.shsoftvina.community.modules.root.example.service.ExampleService;
import com.shsoftvina.community.modules.root.group_noti.service.GroupNotiService;
import com.shsoftvina.community.modules.root.hashtag.service.HashTagService;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.modules.root.notification.service.NotificationService;
import com.shsoftvina.community.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Primary
@Slf4j
public class ComponentServiceImpl extends ComponentEntityServiceImpl implements ComponentService {

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GroupNotiService groupNotiService;

    protected void deleteComponent(Long id) {
        Component component = super.findById(id);

        this.pushNotification(component);

        List<Example> examples = component.getExamples();

        component.setStatus(EStatus.DELETED);
        super.save(component);

        this.deleteHashTagListOfComponent(id);
        this.deleteExample(examples);
        this.deleteComments(id);
    }

    private void pushNotification(Component component) {

        if(groupNotiService.isUserHasGroupAccountActivity(SecurityUtil.getUsernameCurrent())){
            log.debug("Push notification to component id = {}", component.getId());
            notificationService.createNotification(CreateNotificationReq.buildForComponent(component, EActionNotification.DELETE_COMPONENT, true));
        }
    }

    @Async
    public void deleteComments(Long id) {
        log.debug("Delete comments of component id = {}", id);
        commentService.deleteComments(id, ECommentType.COMPONENT);
    }

    @Async
    public void deleteHashTagListOfComponent(Long componentId){
        log.debug("Delete hashtags of component id = {}", componentId);
        hashTagService.deleteHashTagList(componentId, EHashTagType.COMPONENT);
    }

    @Async
    public void deleteExample(List<Example> examples){
        exampleService.deleteAll(examples);
    }
}
