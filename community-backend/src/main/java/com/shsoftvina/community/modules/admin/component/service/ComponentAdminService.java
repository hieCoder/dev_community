package com.shsoftvina.community.modules.admin.component.service;

import com.shsoftvina.community.modules.admin.component.model.req.CreateComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.req.EditComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.req.MoveComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.res.ComponentDetailAdminRes;
import com.shsoftvina.community.modules.admin.component.model.res.ListComponentAdminRes;
import com.shsoftvina.community.modules.root.component.service.ComponentService;

import java.util.List;

public interface ComponentAdminService extends ComponentService {

    ListComponentAdminRes getListComponentAdmin();
    void createComponent(CreateComponentAdminReq req);
    ComponentDetailAdminRes getDetail(Long id);
    List<String> getAllName();
    void moveComponent(MoveComponentAdminReq req);
    void deleteCategory(Long id);
    void editComponent(EditComponentAdminReq req);
}
