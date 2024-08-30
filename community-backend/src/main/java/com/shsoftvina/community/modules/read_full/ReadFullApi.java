package com.shsoftvina.community.modules.read_full;

import com.shsoftvina.community.modules.read_full.service.ReadFullService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/read-full")
@Slf4j
public class ReadFullApi {

    @Autowired
    private ReadFullService readFullService;

    @PutMapping("/post/{postId}")
    public ResponseEntity<Void> updateReadFullPost(@PathVariable Long postId,
                                                   @RequestHeader String ipClient) {
        readFullService.updateReadFull(postId, ipClient);
        return ResponseEntity.noContent().build();
    }
}
