package com.shsoftvina.community.modules.component.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.modules.admin.example.model.res.ExampleDetailAdminRes;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.utils.DateUtils;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class ComponentDetailDevRes {

    private Long id;
    private String title;
    private String description;
    private String whenToUse;
    private CategoryDevRes category;
    private List<HashTagRes> hashTagList;
    private List<ExampleDetailDevRes> examples;
    private String nickNameDefault;
    private List<String> listIpClientLiked;
    private Long totalComment;
    private String fbAppId;

    @Setter
    @Getter
    public static class CategoryDevRes {
        private Long id;
        private String name;
    }

    @Setter
    @Getter
    public static class ExampleDetailDevRes {

        private Long id;
        private String title;
        private String description;
        private String video;
        @JsonIgnore
        private String resource;
        @JsonIgnore
        private String sourceCode;
        @JsonIgnore
        private Instant createdDate;
        @JsonIgnore
        private String cover;

        @JsonProperty("resource")
        public Object getResourceObject() {
            return JsonUtils.jsonToObject(resource, Object.class);
        }

        @JsonProperty("sourceCode")
        public Object getSourceCodeObject() {
            return JsonUtils.jsonToObject(sourceCode, Object.class);
        }

        @JsonProperty("cover")
        private Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }
    }
}
