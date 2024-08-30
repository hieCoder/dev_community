package com.shsoftvina.community.modules.book.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BookDevRes {

    private static final int FIRST_INDEX = 0;

    private Long id;
    private String title;
    @JsonIgnore
    private String cover;
    private String href;

    @JsonProperty("cover")
    private Object getCoverObject() { return JsonUtils.jsonToObject(cover, List.class).get(FIRST_INDEX); }
}
