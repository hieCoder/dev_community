package com.shsoftvina.community.modules.admin.post;

import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.admin.post.model.req.UpdateDrafPostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.req.CreatePostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.req.EditPostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.res.PostAdminRes;
import com.shsoftvina.community.modules.admin.post.model.res.PostDetailAdminRes;
import com.shsoftvina.community.modules.admin.post.service.PostAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/posts")
public class PostAdminApi {

    @Autowired
    private PostAdminService postAdminService;

    @GetMapping
    public ResponseEntity<List<PostAdminRes>> findAll() {
        return ResponseEntity.ok(postAdminService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailAdminRes> findDetail(@PathVariable Long id) {
        return ResponseEntity.ok(postAdminService.findDetail(id));
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostAdminReq req){
        postAdminService.createPost(req);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPost(@Valid @RequestBody EditPostAdminReq req,
                                         @PathVariable Long id){
        if(!id.equals(req.getId())){
            throw new BadRequestAlertException(ErrorEnum.ID_NOT_FOUND);
        }
        postAdminService.editPost(req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePosts(@RequestBody List<Long> ids) {
        postAdminService.deletePosts(ids);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/draf")
    public ResponseEntity<PostAdminRes> updateDrafPost(@RequestBody UpdateDrafPostAdminReq req){
        return ResponseEntity.ok(postAdminService.updateDrafPost(req));
    }

    @PutMapping("/clear-activate-access-detail/{id}")
    public ResponseEntity<Void> clearActivateAccessDetail(@PathVariable Long id){
        postAdminService.clearActivateAccessDetail(id);
        return ResponseEntity.noContent().build();
    }
}