package com.shsoftvina.community.modules.post;

import com.shsoftvina.community.modules.post.model.FilterPostByHashTagRes;
import com.shsoftvina.community.modules.post.model.PostDetailRes;
import com.shsoftvina.community.modules.post.model.PostRes;
import com.shsoftvina.community.modules.post.service.PostDevService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostDevApi {

    @Autowired
    private PostDevService postDevService;

    @GetMapping
    public ResponseEntity<List<PostRes>> findAll() {
        return ResponseEntity.ok(postDevService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailRes> findDetail(@RequestHeader String ipClient, @PathVariable Long id) {
        return ResponseEntity.ok(postDevService.findDetail(ipClient, id));
    }

    @PutMapping("/{id}/reading-time")
    public ResponseEntity<Void> updateReadingTime(@PathVariable Long id, @RequestParam Integer second) {
        postDevService.updateReadingTime(id, second);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter-hashtag/{hashTagId}")
    public ResponseEntity<FilterPostByHashTagRes> findAllByHashTagId(@PathVariable Long hashTagId) {
        return ResponseEntity.ok(postDevService.findAllByHashTagId(hashTagId));
    }

    @PutMapping("/{id}/sharing")
    public ResponseEntity<Void> updateSharing(@PathVariable Long id) {
        postDevService.updateSharing(id);
        return ResponseEntity.noContent().build();
    }
}
