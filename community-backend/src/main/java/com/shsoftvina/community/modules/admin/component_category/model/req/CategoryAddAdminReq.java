package com.shsoftvina.community.modules.admin.component_category.model.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAddAdminReq {

    @NotBlank(message = "name must be not blank")
    private String name;
}
