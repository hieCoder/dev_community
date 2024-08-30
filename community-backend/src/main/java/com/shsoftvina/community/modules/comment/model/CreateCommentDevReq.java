package com.shsoftvina.community.modules.comment.model;

import com.shsoftvina.community.domain.enumration.ECommentType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCommentDevReq {

    private Long eventId;
    private ECommentType eventType;

    private String nickName;

    @NotBlank(message = "nick name is not blank")
    private String content;

    private Long parentId;
    private Boolean isSaveNickName;
}
