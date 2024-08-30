package com.shsoftvina.community.modules.admin.component_category.service.impl;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.admin.component_category.CategoryAdminRepository;
import com.shsoftvina.community.modules.admin.component_category.mapper.CategoryAddAdminReqMapper;
import com.shsoftvina.community.modules.admin.component_category.mapper.CategoryAdminResMapper;
import com.shsoftvina.community.modules.admin.component_category.mapper.CategoryDetailAdminResMapper;
import com.shsoftvina.community.modules.admin.component_category.mapper.CategoryEditAdminReqMapper;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryAddAdminReq;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryEditAdminReq;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryAdminRes;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryDetailAdminRes;
import com.shsoftvina.community.modules.admin.component_category.service.ComponentCategoryAdminService;
import com.shsoftvina.community.modules.admin.user.service.UserAdminService;
import com.shsoftvina.community.modules.root.component_category.service.impl.ComponentCategoryServiceImpl;
import com.shsoftvina.community.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComponentCategoryAdminServiceImpl extends ComponentCategoryServiceImpl implements ComponentCategoryAdminService {

    @Autowired
    private CategoryAddAdminReqMapper categoryAddAdminReqMapper;

    @Autowired
    private CategoryEditAdminReqMapper categoryEditAdminReqMapper;

    @Autowired
    private CategoryDetailAdminResMapper categoryDetailAdminResMapper;

    @Autowired
    private CategoryAdminResMapper categoryAdminResMapper;

    @Autowired
    private CategoryAdminRepository categoryAdminRepository;

    @Autowired
    private UserAdminService userAdminService;

    @Override
    public CategoryAdminRes createCategory(CategoryAddAdminReq req) {
        String nameReq = req.getName();

        this.validateNewCategory(nameReq);

        ComponentCategory category = categoryAddAdminReqMapper.toEntity(req);
        category.setStatus(EStatus.ACTIVATED);
        category.setUser(userAdminService.findByUsername(SecurityUtil.getUsernameCurrent()));
        return categoryAdminResMapper.toDto(super.save(category));
    }

    @Override
    public void editCategory(CategoryEditAdminReq req) {
        Long idReq = req.getId();
        String nameReq = req.getName();

        ComponentCategory category = super.findById(idReq);
        if(!category.getName().equals(nameReq)){
            this.validateNewCategory(nameReq);
        }

        categoryEditAdminReqMapper.partialUpdate(category, req);
        super.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        ComponentCategory category = super.findById(id);
        category.getComponents().stream().peek(c -> c.setCategory(null)).toList();
        category.setStatus(EStatus.DELETED);
        super.save(category);
    }

    @Override
    public CategoryDetailAdminRes findDetail(Long id) {
        return categoryDetailAdminResMapper.toDto(super.findById(id));
    }

    @Override
    public List<CategoryAdminRes> findAllByAdmin() {
        return categoryAdminResMapper.toDto(categoryAdminRepository.findAllByRole(SecurityUtil.getUsernameQuery()));
    }

    @Override
    public List<String> getAllName() {
        return categoryAdminRepository.findAll().stream()
                .map(ComponentCategory::getName).toList();
    }

    private void validateNewCategory(String name) {
        if (name != null){
            if (categoryAdminRepository.existsByName(name))
                throw new BadRequestAlertException(ErrorEnum.COMPONENT_CATEGORY_ALREADY_EXISTED);
        }
    }
}
