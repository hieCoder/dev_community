package com.shsoftvina.community.modules.admin.example.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExampleAdminRes {

    private Long id;
    private String title;
    @JsonIgnore
    private String resource;
    @JsonIgnore
    private String cover;

    @JsonProperty("resource")
    private Object getResourceObject(){
        return JsonUtils.jsonToObject(resource, Object.class);
    }

    @JsonProperty("cover")
    private Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }
}