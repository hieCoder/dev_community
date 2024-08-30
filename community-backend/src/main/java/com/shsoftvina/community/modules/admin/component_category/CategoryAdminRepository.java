package com.shsoftvina.community.modules.admin.component_category;

import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.modules.root.component_category.ComponentCategoryRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryAdminRepository extends ComponentCategoryRepository {

    @Query("select cc from ComponentCategory cc where cc.status = 'ACTIVATED' and cc.id = :id")
    Optional<ComponentCategory> findById(Long id);

    @Query("select (count(cc) > 0) from ComponentCategory cc where cc.name = :name and cc.status = 'ACTIVATED'")
    boolean existsByName(String name);

    @Query("select cc from ComponentCategory cc where cc.status = 'ACTIVATED' and (:username is null or cc.user.username = :username)")
    List<ComponentCategory> findAllByRole(String username);

    @Query("select cc from ComponentCategory cc where cc.status = 'ACTIVATED'")
    List<ComponentCategory> findAll();
}
