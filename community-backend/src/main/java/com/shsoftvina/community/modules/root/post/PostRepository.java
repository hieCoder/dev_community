package com.shsoftvina.community.modules.root.post;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.enumration.EStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.id = :id and p.status in :statuses")
    Optional<Post> findById(@Param("id") Long id, List<EStatus> statuses);

    @Query("select p from Post p where p.id = :id and p.status = 'ACTIVATED'")
    Optional<Post> findById(@Param("id") Long id);

    @Query("select p from Post p where p.status = 'ACTIVATED' " +
            "and (p.title like concat('%', :keyword,'%') or p.content like concat('%', :keyword,'%'))")
    List<Post> findAll(@Param("keyword") String keyword);

    @Query("select p from Post p where p.status <> 'DELETED' and p.id in :ids")
    List<Post> findByIdInNoDelete(List<Long> ids);

    @Query("select p from Post p where p.status = 'ACTIVATED' order by p.postingTime desc")
    List<Post> findAllPost(Pageable pageable);

    @Query("select p from Post p where p.status = 'ACTIVATED' order by p.postingTime desc")
    Page<Post> findAll(Pageable pageable);
}
