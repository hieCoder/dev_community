package com.shsoftvina.community.modules.admin.component.mapper;

import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.modules.admin.component.model.res.ListComponentAdminRes;
import com.shsoftvina.community.utils.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.shsoftvina.community.modules.admin.component.ComponentAdminContants.MAX_COVER_COMPONENT_IN_A_CATEGORY;

@Component
public class ListComponentAdminMapper {

    private final int FIRST_INDEX = 0;

    public ListComponentAdminRes toDto(List<ComponentCategory> categoryEntities, List<com.shsoftvina.community.domain.Component> componentEntities){

        var foldersRes = categoryEntities.stream()
                .sorted(Comparator.comparing(ComponentCategory::getCreatedDate).reversed())
                .map(this::toFolderRes)
                .collect(Collectors.toList());

        var componentsRes = componentEntities.stream()
                .filter(component -> component.getCategory() == null)
                .map(this::toComponentRes)
                .collect(Collectors.toList());

        var result = new ListComponentAdminRes();
        result.setFolders(foldersRes);
        result.setComponents(componentsRes);

        return result;
    }

    private ListComponentAdminRes.ComponentRes toComponentRes(com.shsoftvina.community.domain.Component component) {
        ListComponentAdminRes.ComponentRes componentRes = new ListComponentAdminRes.ComponentRes();
        componentRes.setId(component.getId());
        componentRes.setTitle(component.getTitle());
        componentRes.setResource(component.getExamples().get(FIRST_INDEX).getResource());
        componentRes.setCreatedDate(component.getCreatedDate());
        componentRes.setTotalView(component.getTotalView());
        componentRes.setCover(component.getExamples().get(FIRST_INDEX).getCover());
        return componentRes;
    }

    private ListComponentAdminRes.CategoryRes toFolderRes(ComponentCategory category){
        ListComponentAdminRes.CategoryRes folder = new ListComponentAdminRes.CategoryRes();
        folder.setId(category.getId());
        folder.setName(category.getName());

        List<com.shsoftvina.community.domain.Component> components = category.getComponents();
        int totalComponent = components.size();
        folder.setTotalComponent(totalComponent);
        components.stream()
                .limit(MAX_COVER_COMPONENT_IN_A_CATEGORY)
                .map(c -> c.getExamples().get(FIRST_INDEX).getResource())
                .map(json -> JsonUtils.jsonToObject(json, Object.class))
                .forEach(folder.getResources()::add);

        components.stream()
                .limit(MAX_COVER_COMPONENT_IN_A_CATEGORY)
                .map(c -> c.getExamples().get(FIRST_INDEX).getCover())
                .map(json -> JsonUtils.jsonToObject(json, Object.class))
                .forEach(folder.getCovers()::add);

        return folder;
    }
}
