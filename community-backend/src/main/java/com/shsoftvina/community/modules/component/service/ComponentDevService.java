package com.shsoftvina.community.modules.component.service;

import com.shsoftvina.community.modules.component.model.res.ComponentDetailDevRes;
import com.shsoftvina.community.modules.component.model.res.ListComponentDevRes;
import com.shsoftvina.community.modules.component.model.res.OutstandingComponentRes;
import com.shsoftvina.community.modules.home.outstading.ComponentOutstandingProjection;
import com.shsoftvina.community.modules.root.component.service.ComponentService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComponentDevService extends ComponentService {

    List<OutstandingComponentRes> getListOutstanding(Pageable pageable);
    List<ComponentOutstandingProjection> getListOutstandingComponentInfo();

    ListComponentDevRes findAll();
    ComponentDetailDevRes getDetail(String ipClient, Long id);
    void updateSharing(Long id);
}
