package com.shsoftvina.community.modules.admin.component.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.DateUtils;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ListComponentAdminRes {

    private List<CategoryRes> folders = new ArrayList<>();
    private List<ComponentRes> components = new ArrayList<>();

    @Setter
    @Getter
    public static class CategoryRes {
        private Long id;
        private String name;
        private Integer totalComponent;
        private List<Object> resources = new ArrayList<>();
        private List<Object> covers = new ArrayList<>();
    }

    @Setter
    @Getter
    public static class ComponentRes {
        private Long id;
        private String title;
        private Integer totalView;
        private Instant createdDate;
        @JsonIgnore
        private String resource;
        @JsonIgnore
        private String cover;

        @JsonProperty("resource")
        public Object getResourceObject() { return JsonUtils.jsonToObject(resource, Object.class); }

        @JsonProperty("cover")
        public Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }

        public Integer getTotalView() {
            return totalView == null ? 0 : totalView;
        }
    }
}
