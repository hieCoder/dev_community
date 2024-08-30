package com.shsoftvina.community.model.search.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostSearchRes {
    private Long id;
    private String title;
    @JsonIgnore
    private String cover;
    private String href;
    private String description;

    @JsonProperty("cover")
    public Object getCoverObject() {
        return JsonUtils.jsonToObject(cover, Object.class);
    }
}
