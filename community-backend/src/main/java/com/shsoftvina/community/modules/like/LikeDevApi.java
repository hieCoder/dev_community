package com.shsoftvina.community.modules.like;

import com.shsoftvina.community.domain.enumration.ELikeType;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.like.service.LikeDevService;
import com.shsoftvina.community.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeDevApi {

    @Autowired
    private LikeDevService likeDevService;

    @PostMapping("/{eventType}/{eventId}")
    public ResponseEntity<Void> findAll(@PathVariable String eventType,
                                        @PathVariable Long eventId,
                                        @RequestHeader String ipClient) {
        try {
            ELikeType likeType = ELikeType.valueOf(eventType.toUpperCase());
            likeDevService.likeAction(eventId, likeType, ipClient);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException(ErrorEnum.ENUM_CONVERT_ERROR);
        }
    }
}
