package com.shsoftvina.community.modules.root.post.service;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.post.model.OutstandingPostRes;
import com.shsoftvina.community.modules.post.model.PostDetailRes;
import com.shsoftvina.community.modules.post.model.PostRes;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PostEntityService {

   Post findById(Long id, List<EStatus> statuses);
   List<Post> findAll(String keyword);
   Post save(Post post);
   Post findById(Long id);
}
