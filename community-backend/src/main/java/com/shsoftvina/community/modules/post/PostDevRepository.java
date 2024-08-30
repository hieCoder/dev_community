package com.shsoftvina.community.modules.post;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.modules.home.outstading.PostOutstandingProjection;
import com.shsoftvina.community.modules.root.post.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDevRepository extends PostRepository {

    @Query("select" +
                " p.id as id," +
                " p.title as title," +
                " coalesce(count(distinct c.id), 0) as commentCount," +
                " coalesce(count(distinct l.id), 0) as likeCount," +
                " coalesce(p.totalShare, 0) as shareCount," +
                " coalesce(p.totalView, 0) as viewCount, " +
                " coalesce(p.totalReadSecond, 0) as totalReadSecond, " +
                " coalesce(count(distinct case when r.isReadFull = true then r.id end), 0) as totalReadFullCount " +
            " from Post p" +
            " left join Like l on p.id = l.eventId and l.eventType = 'POST'" +
            " left join Comment c on p.id = c.eventId and c.eventType = 'POST' and c.status = 'ACTIVATED'" +
            " left join ReadFull r on p.id = r.postId" +
            " where p.status = 'ACTIVATED'" +
            " group by p.id")
    List<PostOutstandingProjection> getPostOutstanding();

    @Query("select p from Post p where p.status = 'ACTIVATED' and p.id in :ids")
    List<Post> findByIdIn(@Param("ids") List<Long> ids);

    @Query("select p from Post p join EventHashTag eh on p.id = eh.eventId join HashTag h on eh.hashTagId = h.id" +
            " where h.eventType = 'POST' and p.status = 'ACTIVATED' and p.id <> :postIdCurrent and h.name in :hashTagList" +
            " group by p.id" +
            " order by count(h.name) desc")
    List<Post> findAllOtherPostByHashTagIn(Pageable pageable, Long postIdCurrent, List<String> hashTagList);

    @Query("select p from Post p join EventHashTag eh on p.id = eh.eventId join HashTag h on eh.hashTagId = h.id" +
            " where h.eventType = 'POST' and p.status = 'ACTIVATED' and h.id = :hashTagId")
    List<Post> findAllByHashTagId(Long hashTagId);
}
