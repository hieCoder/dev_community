package com.shsoftvina.community.modules.admin.component;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.modules.root.component.ComponentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComponentAdminRepository extends ComponentRepository {

    @Query("select c from Component c where c.status = 'ACTIVATED'")
    List<Component> findAll();

    @Query("select (count(c) > 0) from Component c where c.title = :title and c.status = 'ACTIVATED'")
    boolean existsByTitle(String title);
}