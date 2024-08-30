package com.shsoftvina.community.modules.root.component;

import com.shsoftvina.community.domain.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository  extends JpaRepository<Component, Long> {

    @Query("select c from Component c where c.status = 'ACTIVATED' " +
            "and (c.title like concat('%', :keyword,'%') or c.description like concat('%', :keyword,'%'))")
    List<Component> findAll(@Param("keyword") String keyword);

    @Query("select c from Component c where c.id = :id and c.status = 'ACTIVATED'")
    Optional<Component> findById(@Param("id") Long id);

    @Query("select c from Component c where c.status = 'ACTIVATED' and (:username is null or c.user.username = :username)")
    List<Component> findAllByRole(String username);
}
