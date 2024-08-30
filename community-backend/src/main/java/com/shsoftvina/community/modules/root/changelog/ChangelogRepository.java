package com.shsoftvina.community.modules.root.changelog;

import com.shsoftvina.community.domain.Changelog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangelogRepository extends JpaRepository<Changelog, Long> {
}
