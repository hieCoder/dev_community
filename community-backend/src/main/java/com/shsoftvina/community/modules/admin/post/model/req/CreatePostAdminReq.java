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
public class CreatePostAdminReq {

    private Long draftId;

    @NotBlank(message = "title is not blank")
    private String title;

    @NotBlank(message = "content is not blank")
    private String content;

    @NotNull(message = "list hashtag is not null")
    private List<String> hashTagList;

    private LocalDate datePost;

    private LocalTime timePost;

    @NotNull(message = "comment permission is not null")
    private ECommentPermission commentPermission;

    @NotNull(message = "example ids is not null")
    private List<Long> exampleIds;

    @JsonIgnore
    private String cover;

    @JsonIgnore
    private String tableContent;

    @JsonIgnore
    private Boolean isSchedulingTemp;

    @JsonIgnore
    private Instant postingTimeTemp;

    @JsonProperty("cover")
    public void setCoverObject(Object coverObject) {
        this.cover = JsonUtils.objectToJson(coverObject);
    }

    @JsonProperty("tableContent")
    public void setTableContentObject(Object tableContentObject) {
        this.tableContent = JsonUtils.objectToJson(tableContentObject);
    }

    public Boolean getIsSchedulingTemp() {
        if(datePost == null || timePost == null) return false;
        return true;
    }

    public Instant getPostingTimeTemp(){
        return DateUtils.mergeToInstant(datePost, timePost);
    }
}
