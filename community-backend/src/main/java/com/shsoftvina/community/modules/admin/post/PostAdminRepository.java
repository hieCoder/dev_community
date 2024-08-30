package com.shsoftvina.community.modules.admin.post;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.modules.root.post.PostRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostAdminRepository extends PostRepository {

    @Query("select p from Post p where p.status <> 'DELETED' and (:username is null or p.user.username = :username) order by p.createdDate desc")
    List<Post> findAllByUsername(String username);
}