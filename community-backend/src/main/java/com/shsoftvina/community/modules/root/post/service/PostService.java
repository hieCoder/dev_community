package com.shsoftvina.community.modules.root.post.service;

import com.shsoftvina.community.domain.Post;

import java.util.List;

public interface PostService extends PostEntityService{

    void saveAll(List<Post> posts);
}
