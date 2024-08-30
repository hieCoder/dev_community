package com.shsoftvina.community.modules.component.mapper;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.component.model.res.OutstandingComponentRes;
import com.shsoftvina.community.utils.DateUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OutstandingComponentResMapper extends EntityMapper<OutstandingComponentRes, Component> {

    @AfterMapping
    default void enrichResWithTypePopular(Component component, @MappingTarget OutstandingComponentRes res) {
        if(DateUtils.isDateInCurrentMonth(component.getCreatedDate())){
            if(!component.getLastModifiedDate().equals(component.getCreatedDate())){
                res.setTypePopular(OutstandingComponentRes.TypePopular.UPDATE);
            } else {
                res.setTypePopular(OutstandingComponentRes.TypePopular.NEW);
            }
        } else {
            res.setTypePopular(OutstandingComponentRes.TypePopular.NONE);
        }
    }
}
