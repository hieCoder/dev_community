package com.shsoftvina.community.modules.admin.component_category.service;

import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryAddAdminReq;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryEditAdminReq;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryAdminRes;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryDetailAdminRes;
import com.shsoftvina.community.modules.root.component_category.service.ComponentCategoryService;

import java.util.List;

public interface ComponentCategoryAdminService extends ComponentCategoryService {
    CategoryAdminRes createCategory(CategoryAddAdminReq req);
    void editCategory(CategoryEditAdminReq req);
    void deleteCategory(Long id);
    CategoryDetailAdminRes findDetail(Long id);
    List<CategoryAdminRes> findAllByAdmin();
    List<String> getAllName();
}
