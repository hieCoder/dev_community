package com.shsoftvina.community.modules.component.mapper;

import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.modules.component.model.res.ListComponentDevRes;
import com.shsoftvina.community.utils.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListComponentDevResMapper {

    private final int FIRST_INDEX = 0;

    public ListComponentDevRes toDto(List<ComponentCategory> categoryEntities, List<com.shsoftvina.community.domain.Component> componentEntities){

        var foldersRes = categoryEntities.stream()
                .sorted(Comparator.comparing(ComponentCategory::getCreatedDate).reversed())
                .map(this::toFolderRes)
                .collect(Collectors.toList());

        var componentsRes = componentEntities.stream()
                .filter(component -> component.getCategory() == null)
                .map(this::toComponentRes)
                .collect(Collectors.toList());

        var result = new ListComponentDevRes();
        result.setFolders(foldersRes);
        result.setComponents(componentsRes);

        return result;
    }

    private ListComponentDevRes.ComponentRes toComponentRes(com.shsoftvina.community.domain.Component component) {
        ListComponentDevRes.ComponentRes componentRes = new ListComponentDevRes.ComponentRes();
        componentRes.setId(component.getId());
        componentRes.setTitle(component.getTitle());
        componentRes.setResource(JsonUtils.jsonToObject(component.getExamples().get(FIRST_INDEX).getResource(), Object.class));
        componentRes.setCover(JsonUtils.jsonToObject(component.getExamples().get(FIRST_INDEX).getCover(), Object.class));
        return componentRes;
    }

    private List<ListComponentDevRes.ComponentRes> toComponentsRes(List<com.shsoftvina.community.domain.Component> components) {
        return components.stream().map(this::toComponentRes).toList();
    }

    private ListComponentDevRes.CategoryRes toFolderRes(ComponentCategory category){
        ListComponentDevRes.CategoryRes folder = new ListComponentDevRes.CategoryRes();
        folder.setId(category.getId());
        folder.setName(category.getName());

        List<com.shsoftvina.community.domain.Component> components = category.getComponents();
        folder.setTotalComponent(components.size());
        folder.setComponents(this.toComponentsRes(components));
        return folder;
    }
}
