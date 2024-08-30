package com.shsoftvina.community.modules.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.domain.enumration.ECommentPermission;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.List;

import static com.shsoftvina.community.constant.PostConstant.READ_MEDIUM_IN_ONE_MINUTE;

@Setter
@Getter
public class PostDetailRes {
    private Long id;
    private String title;
    private Instant postingTime;
    private List<HashTagRes> hashTagList;
    @JsonIgnore
    private String cover;
    private String content;
    private Integer readMediumTime;
    private Long totalComment;
    private UserRes user;
    private ECommentPermission commentPermission;
    private String nickNameDefault;
    private List<String> listIpClientLiked;
    private List<RelatedExampleRes> examples;
    private List<OutstandingPostRes> listRelatedGreatest;
    private String tableContent;
    private String fbAppId;

    @Setter
    @Getter
    public static class UserRes {
        private String username;
    }

    @JsonProperty("cover")
    private Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }

    @JsonProperty("tableContent")
    private Object getTableContentObject() { return JsonUtils.jsonToObject(tableContent, Object.class); }

    public Integer getReadMediumTime(){
        return this.content.split(" ").length/READ_MEDIUM_IN_ONE_MINUTE;
    }

    @Setter
    @Getter
    public static class RelatedExampleRes {
        private ComponentRes component;
        private String title;
        @JsonIgnore
        private String resource;
        @JsonIgnore
        private String cover;

        private UserRes user;

        @JsonProperty("resource")
        private Object getResourceObject() { return JsonUtils.jsonToObject(resource, Object.class); }

        @JsonProperty("cover")
        private Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }

        @Setter
        @Getter
        public static class ComponentRes {
            private Long id;
        }

        @Setter
        @Getter
        public static class UserRes {

            private String avatar;

            @JsonProperty("avatar")
            private Object getAvatarObject() { return JsonUtils.jsonToObject(avatar, Object.class); }
        }
    }
}
