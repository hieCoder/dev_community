package com.shsoftvina.community.modules.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

import static com.shsoftvina.community.constant.PostConstant.READ_MEDIUM_IN_ONE_MINUTE;

@Setter
@Getter
public class PostRes {

    private Long id;
    private String cover;
    private Instant createdDate;
    private Integer totalView;
    private String title;
    private String content;
    private Integer readMediumTime;
    private Long totalComment;
    private List<HashTagRes> hashTagList;
    private UserRes user;

    public Integer getReadMediumTime(){
        return this.content.split(" ").length/READ_MEDIUM_IN_ONE_MINUTE;
    }

    @Setter
    @Getter
    public static class UserRes {
        private String username;
        private String avatar;
    }
}
