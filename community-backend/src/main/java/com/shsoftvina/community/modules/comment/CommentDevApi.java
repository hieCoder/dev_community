package com.shsoftvina.community.modules.comment;

import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.comment.model.CommentDevRes;
import com.shsoftvina.community.modules.comment.model.CreateCommentDevReq;
import com.shsoftvina.community.modules.comment.service.CommentDevService;
import com.shsoftvina.community.modules.ip.service.IpDevService;
import com.shsoftvina.community.service.SocketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentDevApi {

    @Autowired
    private CommentDevService commentDevService;

    @Autowired
    private SocketService socketService;

    @GetMapping("/{eventType}/{eventId}")
    public ResponseEntity<List<CommentDevRes>> findAll(@PathVariable String eventType, @PathVariable Long eventId) {
        try {
            ECommentType commentType = ECommentType.valueOf(eventType.toUpperCase());
            return ResponseEntity.ok(commentDevService.findAllByEvent(eventId, commentType));
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException(ErrorEnum.ENUM_CONVERT_ERROR);
        }
    }

    @PostMapping("/{eventType}/{eventId}")
    public ResponseEntity<Void> createComment(@RequestHeader String ipClient,
                                              @PathVariable String eventType,
                                              @PathVariable Long eventId,
                                              @Valid @RequestBody CreateCommentDevReq req) {
        ECommentType commentType = ECommentType.valueOf(eventType.toUpperCase());

        req.setEventId(eventId);
        req.setEventType(commentType);

        CommentDevRes commentDevRes = commentDevService.createComment(ipClient, req);
        if(commentDevRes != null){
            socketService.pushWSToClientCreateComment(eventId, commentType, commentDevRes);
        }
        return ResponseEntity.noContent().build();
    }
}
