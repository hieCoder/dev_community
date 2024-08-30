package com.shsoftvina.community.modules.admin.post.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.domain.enumration.ECommentPermission;
import com.shsoftvina.community.utils.DateUtils;
import com.shsoftvina.community.utils.JsonUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
public class UpdateDrafPostAdminReq {

    private Long id;
    private String title;
    private String content;
    private List<String> hashTagList;
    private LocalDate datePost;
    private LocalTime timePost;
    private ECommentPermission commentPermission;
    private List<Long> exampleIds;

    @JsonIgnore
    private String cover;

    @JsonProperty("cover")
    public void setCoverObject(Object coverObject) {
        this.cover = JsonUtils.objectToJson(coverObject);
    }

    public Boolean getIsSchedulingTemp() {
        if(datePost == null || timePost == null) return false;
        return true;
    }

    public Instant getPostingTimeTemp(){
        return DateUtils.mergeToInstant(datePost, timePost);
    }
}
