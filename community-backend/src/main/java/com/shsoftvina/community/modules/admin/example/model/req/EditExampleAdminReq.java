package com.shsoftvina.community.modules.admin.example.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditExampleAdminReq {

    private Long id;
    @NotBlank(message = "title is not blank")
    private String title;
    private String video;
    @NotBlank(message = "description is not blank")
    private String description;
    @NotBlank(message = "resource is not blank")
    private String resource;
    @JsonIgnore
    private String sourceCode;
    @JsonIgnore
    private String cover;
    private String commit;

    public void setResource(Object resourceObject) {
        this.resource = JsonUtils.objectToJson(resourceObject);
    }

    @JsonProperty("sourceCode")
    public void setSourceCodeObject(Object sourceCodeObject) {
        this.sourceCode = JsonUtils.objectToJson(sourceCodeObject);
    }

    @JsonProperty("cover")
    public void setCoverObject(Object coverObject) {
        this.cover = JsonUtils.objectToJson(coverObject);
    }
}
