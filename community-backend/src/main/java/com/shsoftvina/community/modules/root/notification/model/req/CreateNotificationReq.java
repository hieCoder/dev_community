package com.shsoftvina.community.modules.root.notification.model.req;

import com.shsoftvina.community.domain.*;
import com.shsoftvina.community.domain.enumration.*;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.shsoftvina.community.domain.enumration.EActionNotification.*;
import static com.shsoftvina.community.domain.enumration.EGroupNoti.ACCOUNT_ACTIVITY;
import static com.shsoftvina.community.domain.enumration.EGroupNoti.SOCIAL_INTERACTIONS;
import static com.shsoftvina.community.domain.enumration.ENotificationType.*;

@Setter
@Getter
public class CreateNotificationReq {

    private static final String BASE_HREF_POST = "{baseHrefPost}/";
    private static final String BASE_HREF_COMPONENT = "{baseHrefComponent}/";

    private String href = null;
    private boolean isRead = false;
    private Long eventId;
    private ENotificationType notificationType;
    private EActionNotification action;
    private EGroupNoti groupCode;

    private boolean isNotiForSocial = false;
    private boolean isNotiForLike = false;

    private Map<String, Object> metadataMap = new HashMap<>();

    public static CreateNotificationReq buildForComment(Comment comment){

        ECommentType commentType = comment.getEventType();

        CreateNotificationReq req = setInfoForSocial(comment.getEventId(), commentType.toString());
        if(commentType.equals(ECommentType.POST)){
            req.setAction(NEW_AND_REPLY_COMMENT_POST);
        } else if (commentType.equals(ECommentType.COMPONENT)){
            req.setAction(NEW_AND_REPLY_COMMENT_COMPONENT);
        } else {
            throw new BadRequestAlertException(ErrorEnum.NOT_SUPPORT);
        }

        User userOfComment = comment.getUser();
        req.getMetadataMap().putAll(Map.of("message", comment.getContent(),
                "actor", userOfComment == null ? comment.getNickName() : userOfComment.getUsername()));

        return req;
    }

    public static CreateNotificationReq buildForLike(Like like){

        ELikeType likeType = like.getEventType();

        CreateNotificationReq req = setInfoForSocial(like.getEventId(), likeType.toString());
        if(likeType.equals(ELikeType.POST)){
            req.setAction(POST_LIKED);
        } else if (likeType.equals(ELikeType.COMPONENT)){
            req.setAction(COMPONENT_LIKED);
        } else {
            throw new BadRequestAlertException(ErrorEnum.NOT_SUPPORT);
        }
        req.setNotiForLike(true);
        req.getMetadataMap().put("actor", like.getUser() != null ? like.getUser().getUsername() : like.getIp() != null ? like.getIp().getNickName() : null);

        return req;
    }

    public static CreateNotificationReq buildForPost(Post post, EActionNotification action, boolean isNoHref){

        Long postId = post.getId();
        CreateNotificationReq req = new CreateNotificationReq();

        if(!isNoHref) req.setHref(BASE_HREF_POST + postId);

        req.setEventId(postId);
        req.setNotificationType(POST);
        req.setAction(action);
        req.setGroupCode(ACCOUNT_ACTIVITY);
        req.getMetadataMap().put("title", post.getTitle());

        return req;
    }

    public static CreateNotificationReq buildForComponent(Component component, EActionNotification action, boolean isNoHref){

        Long componentId = component.getId();
        CreateNotificationReq req = new CreateNotificationReq();

        if(!isNoHref) req.setHref(BASE_HREF_COMPONENT + componentId);

        req.setEventId(componentId);
        req.setNotificationType(COMPONENT);
        req.setAction(action);
        req.setGroupCode(ACCOUNT_ACTIVITY);
        req.getMetadataMap().put("title", component.getTitle());

        return req;
    }

    public static CreateNotificationReq buildForChangePassword(User user){

        CreateNotificationReq req = new CreateNotificationReq();
        req.setEventId(user.getId());
        req.setNotificationType(USER);
        req.setAction(PASSWORD_CHANGE);
        req.setGroupCode(ACCOUNT_ACTIVITY);
        req.getMetadataMap().put("time", Instant.now().toString());

        return req;
    }

    private static CreateNotificationReq setInfoForSocial(Long eventId, String eventType){

        CreateNotificationReq req = new CreateNotificationReq();

        String myHref = "";
        ENotificationType myEventType = null;
        if(eventType.equals("POST")){
            myHref = BASE_HREF_POST + eventId;
            myEventType = POST;
        } else if (eventType.equals("COMPONENT")){
            myHref = BASE_HREF_COMPONENT + eventId;
            myEventType = COMPONENT;
        } else {
            throw new BadRequestAlertException(ErrorEnum.NOT_SUPPORT);
        }

        req.setEventId(eventId);
        req.setNotificationType(myEventType);
        req.setHref(myHref);
        req.setGroupCode(SOCIAL_INTERACTIONS);
        req.setNotiForSocial(true);

        return req;
    }
}
