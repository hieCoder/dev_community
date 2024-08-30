package com.shsoftvina.community.modules.admin.book.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class BookAdminRes {

    private Long id;
    private String title;
    private Instant createdDate;
    private String href;
    @JsonIgnore
    private String cover;

    @JsonProperty("cover")
    private Object getCoverObject() { return JsonUtils.jsonToObject(cover, Object.class); }
}
