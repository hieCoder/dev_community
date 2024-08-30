package com.shsoftvina.community.modules.root.example;

import com.shsoftvina.community.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExampleRepository extends JpaRepository<Example, Long> {

    @Query("select e from Example e join fetch e.component c " +
            " where e.status = 'ACTIVATED' and (:keyword is null or e.title like concat('%', :keyword,'%') or e.description like concat('%', :keyword,'%'))")
    List<Example> findAll(@Param("keyword") String keyword);

    @Query("select e from Example e where e.status = 'ACTIVATED' and e.id in :ids")
    List<Example> findByIdIn(List<Long> ids);

    @Query("select e from Example e where e.id = :id and e.status = 'ACTIVATED'")
    Optional<Example> findById(Long id);
}