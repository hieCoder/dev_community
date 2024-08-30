package com.shsoftvina.community.modules.admin.book.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditBookAdminReq {

    @NotNull(message = "id is not null")
    private Long id;

    @NotBlank(message = "title is not blank")
    private String title;

    @NotBlank(message = "href is not blank")
    private String href;

    @JsonIgnore
    private String cover;

    @JsonProperty("cover")
    public void setCoverObject(Object coverObject) {
        this.cover = JsonUtils.objectToJson(coverObject);
    }
}
