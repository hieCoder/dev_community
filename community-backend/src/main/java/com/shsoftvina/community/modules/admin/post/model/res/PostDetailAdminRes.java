package com.shsoftvina.community.modules.admin.post.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.domain.enumration.ECommentPermission;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.modules.post.model.PostDetailRes;
import com.shsoftvina.community.utils.DateUtils;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class PostDetailAdminRes {

    private Long id;
    private String title;
    private String content;
    @JsonIgnore
    private String cover;
    private List<HashTagRes> hashTagList;
    @JsonIgnore
    private Instant postingTime;
    private LocalDate scheduleDate;
    private LocalTime scheduleTime;
    private ECommentPermission commentPermission;
    @JsonIgnore
    private String tableContent;
    private List<RelatedExampleRes> examples;
    @JsonIgnore
    private Boolean isScheduling;

    @JsonProperty("cover")
    private Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }

    public LocalDate getScheduleDate() {
        if(isScheduling == null || !isScheduling) return null;
        return DateUtils.getLocalDate(postingTime);
    }

    public LocalTime getScheduleTime() {
        if(isScheduling == null || !isScheduling) return null;
        return DateUtils.getLocalTime(postingTime);
    }

    @JsonProperty("tableContent")
    public Object getTableContentObject() {
        return JsonUtils.jsonToObject(tableContent, Object.class);
    }

    @Setter
    @Getter
    public static class RelatedExampleRes {
        private Long id;
        private String title;
        @JsonIgnore
        private String resource;
        @JsonIgnore
        private String cover;

        @JsonProperty("cover")
        public Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }

        @JsonProperty("resource")
        public Object getResourceObject() { return JsonUtils.jsonToObject(resource, Object.class); }
    }
}