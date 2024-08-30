package com.shsoftvina.community.modules.admin.component_category.model.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryEditAdminReq {

    @NotNull(message = "id must be not null")
    private Long id;

    @NotBlank(message = "name must be not blank")
    private String name;
}
