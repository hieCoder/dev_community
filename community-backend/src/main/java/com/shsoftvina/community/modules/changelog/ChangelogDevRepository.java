package com.shsoftvina.community.modules.changelog;

import com.shsoftvina.community.domain.Changelog;
import com.shsoftvina.community.modules.root.changelog.ChangelogRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChangelogDevRepository extends ChangelogRepository {

    @Query("select c from Changelog c where c.componentId = :componentId order by c.createdDate desc")
    List<Changelog> findAllByComponentId(Long componentId);
}
