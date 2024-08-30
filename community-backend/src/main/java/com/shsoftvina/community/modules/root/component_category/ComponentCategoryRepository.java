package com.shsoftvina.community.modules.root.component_category;

import com.shsoftvina.community.domain.ComponentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComponentCategoryRepository extends JpaRepository<ComponentCategory, Long> {

    @Query("select cc from ComponentCategory cc where cc.status = 'ACTIVATED' and (:username is null or cc.user.username = :username)")
    List<ComponentCategory> findAllByRole(String username);

    @Query("select cc from ComponentCategory cc where cc.id = :id and cc.status = 'ACTIVATED'")
    Optional<ComponentCategory> findById(Long id);
}
