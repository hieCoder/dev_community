package com.shsoftvina.community.modules.admin.post.model.res;

import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class PostAdminRes {

    private Long id;
    private String title;
    private String content;
    private Instant createdDate;
    private Integer totalView;
    private List<HashTagRes> hashTagList;
    private EStatus status;
    private Instant lastModifiedDate;
}
