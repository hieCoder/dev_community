package com.shsoftvina.community.modules.home;

import com.shsoftvina.community.modules.book.model.BookDevRes;
import com.shsoftvina.community.modules.book.service.BookDevService;
import com.shsoftvina.community.modules.component.model.res.OutstandingComponentRes;
import com.shsoftvina.community.modules.component.service.ComponentDevService;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.modules.hashtag.service.HashtagDevService;
import com.shsoftvina.community.modules.home.outstading.ComponentOutstandingProjection;
import com.shsoftvina.community.modules.home.outstading.PostOutstandingProjection;
import com.shsoftvina.community.modules.post.model.OutstandingPostRes;
import com.shsoftvina.community.modules.post.service.PostDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home/outstanding")
public class HomeApi {

    @Autowired
    private HashtagDevService hashtagDevService;

    @Autowired
    private PostDevService postDevService;

    @Autowired
    private ComponentDevService componentDevService;

    @Autowired
    private BookDevService bookDevService;

    @GetMapping(value = "/hash-tags/post")
    public ResponseEntity<List<HashTagRes>> getListHashtagPostOutstanding() {
        return ResponseEntity.ok(hashtagDevService.getListHashtagPostOutstanding());
    }

    @GetMapping(value = "/post/info")
    public ResponseEntity<List<PostOutstandingProjection>> getListOutstandingPostInfo() {
        return ResponseEntity.ok(postDevService.getListOutstandingPostInfo());
    }

    @GetMapping(value = "/component/info")
    public ResponseEntity<List<ComponentOutstandingProjection>> getListOutstandingComponentInfo() {
        return ResponseEntity.ok(componentDevService.getListOutstandingComponentInfo());
    }

    @GetMapping(value = "/post/hot-topic")
    public ResponseEntity<List<OutstandingPostRes>> getListOutstandingHotTopicPost(@PageableDefault(size = 4) Pageable pageable) {
        return ResponseEntity.ok(postDevService.getListOutstandingHotTopic(pageable));
    }

    @GetMapping(value = "/component/popular")
    public ResponseEntity<List<OutstandingComponentRes>> getListOutstandingComponent(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.ok(componentDevService.getListOutstanding(pageable));
    }

    @GetMapping(value = "/post/latest-and-greatest")
    public ResponseEntity<List<OutstandingPostRes>> getListOutstandingLatestAndGreatestPost(@PageableDefault(size = 6) Pageable pageable) {
        return ResponseEntity.ok(postDevService.getListOutstandingLatestAndGreatest(pageable));
    }

    @GetMapping(value = "/post/new-and-noteworthy")
    public ResponseEntity<List<OutstandingPostRes>> getListOutstandingNewAndNoteworthyPost(@PageableDefault(size = 6) Pageable pageable) {
        return ResponseEntity.ok(postDevService.getListOutstandingNewAndNoteworthy(pageable));
    }

    @GetMapping(value = "/book")
    public ResponseEntity<List<BookDevRes>> getListOutstandingBook(@PageableDefault(size = 8) Pageable pageable) {
        return ResponseEntity.ok(bookDevService.getListOutstandingBook(pageable));
    }
}