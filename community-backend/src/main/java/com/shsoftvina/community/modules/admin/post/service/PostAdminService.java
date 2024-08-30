package com.shsoftvina.community.modules.admin.post.service;

import com.shsoftvina.community.modules.admin.post.model.req.UpdateDrafPostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.req.CreatePostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.req.EditPostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.res.PostAdminRes;
import com.shsoftvina.community.modules.admin.post.model.res.PostDetailAdminRes;
import com.shsoftvina.community.modules.root.post.service.PostService;

import java.util.List;

public interface PostAdminService extends PostService {

    List<PostAdminRes> findAll();
    void createPost(CreatePostAdminReq req);
    void deletePosts(List<Long> ids);
    PostAdminRes updateDrafPost(UpdateDrafPostAdminReq req);
    void editPost(EditPostAdminReq req);
    PostDetailAdminRes findDetail(Long id);
    void clearActivateAccessDetail(Long id);
}
