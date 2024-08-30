package com.shsoftvina.community.modules.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CommentDevRes {

    private Long id;
    private String content;
    private Long parentId;
    private Instant createdDate;
    private String nickName;
    private Object avatar;
    @JsonIgnore
    private UserDevRes user;

    @Getter
    @Setter
    public static class UserDevRes {
        private String username;
        private String avatar;
    }

    public String getNickName(){
        return user!= null ? user.getUsername() : nickName;
    }

    public Object getAvatar() { return user!= null ? JsonUtils.jsonToObject(user.avatar, Object.class): null; }
}
