package com.shsoftvina.community.modules.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class OutstandingPostRes {

    private Long id;
    @JsonIgnore
    private String cover;
    private String title;
    private String content;
    private Integer totalView;
    private Instant createdDate;
    private List<HashTagRes> hashTagList;
    private UserRes user;

    @JsonProperty("cover")
    private Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }

    @Setter
    @Getter
    public static class UserRes {

        private String username;

        @JsonIgnore
        private String avatar;

        @JsonProperty("avatar")
        private Object getAvatarObject() { return JsonUtils.jsonToObject(avatar, Object.class); }
    }
}
