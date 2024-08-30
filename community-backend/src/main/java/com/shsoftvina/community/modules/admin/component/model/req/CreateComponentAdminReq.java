package com.shsoftvina.community.modules.admin.component.model.req;

import com.shsoftvina.community.modules.admin.example.model.req.CreateExampleAdminReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateComponentAdminReq {

    private Long categoryId;

    @NotBlank(message = "title is not blank")
    private String title;

    private String description;

    @NotBlank(message = "when to use  is not blank")
    private String whenToUse;

    @NotNull(message = "list hashtag is not null")
    private List<String> hashTagList;

    @NotNull(message = "examples is not null")
    private List<CreateExampleAdminReq> examples;
}
