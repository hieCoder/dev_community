package com.shsoftvina.community.modules.root.group_noti;

import com.shsoftvina.community.domain.GroupNoti;
import com.shsoftvina.community.domain.enumration.EGroupNoti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupNotiRepository extends JpaRepository<GroupNoti, Long> {

    @Query("select g from GroupNoti g where g.code = :code")
    Optional<GroupNoti> findByCode(EGroupNoti code);

    @Query("select g from GroupNoti g join g.users u where u.username = :username")
    List<GroupNoti> findAllByUser(String username);

    @Query("select g from GroupNoti g where g.code in :codes")
    List<GroupNoti> findByCodes(List<EGroupNoti> codes);
}
