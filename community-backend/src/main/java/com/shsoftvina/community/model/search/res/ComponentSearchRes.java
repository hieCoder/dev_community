package com.shsoftvina.community.model.search.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ComponentSearchRes {
    private Long id;
    private String title;
    @JsonIgnore
    private List<ExampleRes> examples;
    private String href;
    private String description;

    @JsonProperty("resource")
    public Object getResourceObject() { return JsonUtils.jsonToObject(examples.get(0).getResource(), Object.class); }

    @JsonProperty("cover")
    private Object getCoverObject() { return JsonUtils.jsonToObject(examples.get(0).getCover(), Object.class); }

    @Setter
    @Getter
    public static class ExampleRes {
        private String resource;
        private String cover;
    }
}
