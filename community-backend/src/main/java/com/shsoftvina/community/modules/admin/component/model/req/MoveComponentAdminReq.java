package com.shsoftvina.community.modules.admin.component.model.req;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MoveComponentAdminReq {

    private Long id;
    private CategoryReq category;

    @Setter
    @Getter
    public static class CategoryReq {
        private Long id;
        private String name;
    }
}
