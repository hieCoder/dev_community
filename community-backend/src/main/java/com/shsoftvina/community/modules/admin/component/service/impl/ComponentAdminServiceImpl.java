package com.shsoftvina.community.modules.admin.component.service.impl;

import com.shsoftvina.community.domain.*;
import com.shsoftvina.community.domain.enumration.EActionNotification;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.domain.enumration.ERole;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.admin.component.ComponentAdminRepository;
import com.shsoftvina.community.modules.admin.component.mapper.ComponentDetailAdminResMapper;
import com.shsoftvina.community.modules.admin.component.mapper.CreateComponentAdminMapper;
import com.shsoftvina.community.modules.admin.component.mapper.EditComponentAdminMapper;
import com.shsoftvina.community.modules.admin.component.mapper.ListComponentAdminMapper;
import com.shsoftvina.community.modules.admin.component.model.req.CreateComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.req.EditComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.req.MoveComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.res.ComponentDetailAdminRes;
import com.shsoftvina.community.modules.admin.component.model.res.ListComponentAdminRes;
import com.shsoftvina.community.modules.admin.component.service.ComponentAdminService;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryAddAdminReq;
import com.shsoftvina.community.modules.admin.component_category.service.ComponentCategoryAdminService;
import com.shsoftvina.community.modules.admin.example.model.req.CreateExampleAdminReq;
import com.shsoftvina.community.modules.admin.example.model.req.EditExampleAdminReq;
import com.shsoftvina.community.modules.admin.example.service.ExampleAdminService;
import com.shsoftvina.community.modules.admin.group_noti.service.GroupNotiAdminService;
import com.shsoftvina.community.modules.admin.hashtag.service.HashTagAdminService;
import com.shsoftvina.community.modules.admin.notification.service.NotificationAdminService;
import com.shsoftvina.community.modules.admin.user.service.UserAdminService;
import com.shsoftvina.community.modules.root.component.service.impl.ComponentServiceImpl;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.security.SecurityUtil;
import com.shsoftvina.community.config.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComponentAdminServiceImpl extends ComponentServiceImpl implements ComponentAdminService {

    @Autowired
    private ComponentAdminRepository componentAdminRepository;

    @Autowired
    private ListComponentAdminMapper listComponentAdminMapper;

    @Autowired
    private HashTagAdminService hashTagAdminService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private CreateComponentAdminMapper createComponentAdminMapper;

    @Autowired
    private ComponentCategoryAdminService componentCategoryAdminService;

    @Autowired
    private ExampleAdminService exampleAdminService;

    @Autowired
    private ComponentDetailAdminResMapper componentDetailAdminResMapper;

    @Autowired
    private EditComponentAdminMapper editComponentAdminMapper;

    @Autowired
    private NotificationAdminService notificationAdminService;

    @Autowired
    private GroupNotiAdminService groupNotiAdminService;

    @Override
    public ListComponentAdminRes getListComponentAdmin() {
        List<Component> components = componentAdminRepository.findAllByRole(SecurityUtil.getUsernameQuery());
        List<ComponentCategory> categories = componentCategoryAdminService.findAllByRole(SecurityUtil.getUsernameQuery());
        return listComponentAdminMapper.toDto(categories, components);
    }

    @Override
    public void createComponent(CreateComponentAdminReq req) {
        Long categoryId = req.getCategoryId();
        List<CreateExampleAdminReq> exampleReqs = req.getExamples();
        String titleReq = req.getTitle();

        this.validateNewComponent(titleReq);
        exampleAdminService.checkNewExample(exampleReqs);

        req.setExamples(null);
        Component component = createComponentAdminMapper.toEntity(req);
        component.setUser(userAdminService.findByUsername(SecurityUtils.getCurrentUserLogin()));

        if(categoryId != null){
            component.setCategory(componentCategoryAdminService.findById(categoryId));
        }

        component = super.save(component);
        Long componentId = component.getId();
        exampleAdminService.createExamples(exampleReqs, component);

        this.updateHashTagListForCreateComponent(req.getHashTagList(), componentId);

        if(this.isUserCurrentTurnOnGroup()){
            notificationAdminService.createNotification(CreateNotificationReq.buildForComponent(component, EActionNotification.CREATE_COMPONENT, false));
        }
    }

    private boolean isUserCurrentTurnOnGroup() {
        String username = SecurityUtil.getUsernameCurrent();
        return groupNotiAdminService.isUserHasGroupAccountActivity(username);
    }

    @Override
    public ComponentDetailAdminRes getDetail(Long id) {

        Component component = super.findById(id);
        User userCurrent = userAdminService.findByUsername(SecurityUtil.getUsernameCurrent());
        if(!userCurrent.getRole().equals(ERole.SUPER_ADMIN)
                && !userAdminService.findByUsername(SecurityUtil.getUsernameCurrent()).getUsername().equals(component.getUser().getUsername())){
            throw new BadRequestAlertException(ErrorEnum.NO_PERMISSION);
        }

        ComponentDetailAdminRes res = componentDetailAdminResMapper.toDto(component);
        res.setHashTagList(hashTagAdminService.getListHashTagResOfComponent(id));
        return res;
    }

    @Override
    public List<String> getAllName() {
        return componentAdminRepository.findAll().stream()
                .map(Component::getTitle).toList();
    }

    @Override
    public void moveComponent(MoveComponentAdminReq req) {
        Long componentId = req.getId();
        Long categoryId = req.getCategory().getId();
        String categoryNameReq = req.getCategory().getName();

        Component component = super.findById(componentId);
        if(categoryId != null){
            component.setCategory(componentCategoryAdminService.findById(categoryId));
        } else {
            CategoryAddAdminReq categoryAddAdminReq = new CategoryAddAdminReq();
            categoryAddAdminReq.setName(categoryNameReq);
            component.setCategory(componentCategoryAdminService.findById(componentCategoryAdminService.createCategory(categoryAddAdminReq).getId()));
        }
        super.save(component);
    }

    @Override
    public void deleteCategory(Long id) {
        this.deleteComponent(id);
    }

    @Override
    public void editComponent(EditComponentAdminReq req) {
        String titleReq = req.getTitle();
        Long idReq = req.getId();
        Long categoryId = req.getCategoryId();
        List<String> hashTagListReq = req.getHashTagList();
        List<EditExampleAdminReq> examplesReq = req.getExamples();

        Component component = super.findById(idReq);

        if(!component.getTitle().equals(titleReq)){
            this.validateNewComponent(titleReq);
        }

        req.setExamples(null);
        editComponentAdminMapper.partialUpdate(component, req);

        if(categoryId != null){
            component.setCategory(componentCategoryAdminService.findById(categoryId));
        } else component.setCategory(null);

        component = super.save(component);

        this.updateHashTagListForEditComponent(hashTagListReq, idReq);
        this.updateExampleListForEditComponent(examplesReq, component);

        if(this.isUserCurrentTurnOnGroup()){
            notificationAdminService.createNotification(CreateNotificationReq.buildForComponent(component, EActionNotification.UPDATE_COMPONENT, false));
        }
    }

    @Async
    public void updateExampleListForEditComponent(List<EditExampleAdminReq> examplesReq, Component component) {
        exampleAdminService.updateExamples(examplesReq, component);
    }

    @Async
    public void updateHashTagListForEditComponent(List<String> hashTagList, Long componentId) {
        hashTagAdminService.deleteHashTagList(componentId, EHashTagType.COMPONENT);
        this.updateHashTagListForCreateComponent(hashTagList, componentId);
    }

    private void updateHashTagListForCreateComponent(List<String> hashTagList, Long componentId) {
        hashTagAdminService.updateHashTagListOfConponent(hashTagList, componentId);
    }

    private void validateNewComponent(String title) {
        if (title != null){
            if (componentAdminRepository.existsByTitle(title))
                throw new BadRequestAlertException(ErrorEnum.COMPONENT_ALREADY_EXISTED);
        }
    }
}