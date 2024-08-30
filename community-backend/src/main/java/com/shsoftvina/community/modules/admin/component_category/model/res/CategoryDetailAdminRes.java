package com.shsoftvina.community.modules.admin.component_category.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class CategoryDetailAdminRes {

    private static final int FIRST_INDEX = 0;

    private Long id;
    private String name;
    private List<ComponentRes> components;

    @Setter
    @Getter
    public static class ComponentRes{
        private Long id;
        private String title;
        private Instant createdDate;
        private String resource;
        private String cover;

        @JsonIgnore
        private List<ExampleRes> examples;

        @JsonProperty("resource")
        private Object getResourceObject() { return JsonUtils.jsonToObject(examples.get(0).getResource(), Object.class); }

        @JsonProperty("cover")
        private Object getCoverObject() { return JsonUtils.jsonToObject(examples.get(0).getCover(), Object.class); }
    }

    @Setter
    @Getter
    public static class ExampleRes {
        private String resource;
        private String cover;
    }
}
