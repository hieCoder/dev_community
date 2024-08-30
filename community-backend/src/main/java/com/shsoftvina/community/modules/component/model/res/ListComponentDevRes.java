package com.shsoftvina.community.modules.component.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ListComponentDevRes {

    private List<ListComponentDevRes.CategoryRes> folders = new ArrayList<>();
    private List<ListComponentDevRes.ComponentRes> components = new ArrayList<>();

    @Setter
    @Getter
    public static class CategoryRes {
        private Long id;
        private String name;
        private Integer totalComponent;
        private List<ComponentRes> components = new ArrayList<>();
    }

    @Setter
    @Getter
    public static class ComponentRes {
        private Long id;
        private String title;
        private Object resource; // example first
        private Object cover;// example first
    }
}
