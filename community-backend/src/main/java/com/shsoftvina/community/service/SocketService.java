package com.shsoftvina.community.service;

import com.shsoftvina.community.domain.enumration.ECommentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    private final Logger log = LoggerFactory.getLogger(SocketService.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public SocketService(SimpMessageSendingOperations messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    private void pushWSToClient(String destination, Object data){
        log.debug("[Socket] Push data to client by {} with body: {}", destination, data);
        messagingTemplate.convertAndSend(destination, data);
    }

    public void pushWSToClientCreateComment(Long eventId, ECommentType commentType, Object data){
        log.debug("[Socket - Create comment] Socket to client with body : {}", data);
        String destination = "/topic/comment/" + commentType.name().toLowerCase() + "/create/" + eventId;
        this.pushWSToClient(destination, data);
    }

    public void pushWSToClientNotificationIcon(Long userId, Object data){
        log.debug("[Socket - Notification icon] Socket to client with body : {}", data);
        String destination = "/topic/notification/global/" + userId;
        this.pushWSToClient(destination, data);
    }
}
