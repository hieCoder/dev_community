package com.shsoftvina.community.modules.admin.component.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.modules.admin.example.model.res.ExampleDetailAdminRes;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ComponentDetailAdminRes {

    private Long id;
    private String title;
    private String description;
    private String whenToUse;
    private CategoryRes category;
    private List<HashTagRes> hashTagList;
    private List<ExampleDetailAdminRes> examples;

    @Setter
    @Getter
    public static class CategoryRes {
        private Long id;
        private String name;
    }
}
