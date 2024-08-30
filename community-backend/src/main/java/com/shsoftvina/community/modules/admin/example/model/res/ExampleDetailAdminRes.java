package com.shsoftvina.community.modules.admin.example.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.DateUtils;
import com.shsoftvina.community.utils.JsonUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class ExampleDetailAdminRes {

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

    @JsonProperty("countDayPublished")
    private long getCountDayPublished() {
        return DateUtils.getCountDayUtilToday(createdDate);
    }

    @JsonProperty("cover")
    private Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }
}
