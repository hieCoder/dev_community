package com.shsoftvina.community.modules.admin.group_noti;

import com.shsoftvina.community.domain.enumration.EGroupNoti;
import com.shsoftvina.community.modules.admin.group_noti.service.GroupNotiAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/group-noti")
public class GroupNotiAdminApi {

    @Autowired
    private GroupNotiAdminService groupNotiAdminService;

    @GetMapping
    public ResponseEntity<List<EGroupNoti>> getGroups(){
        return ResponseEntity.ok(groupNotiAdminService.findAllByUserCurrent());
    }

    @PutMapping
    public ResponseEntity<Void> updateGroup(@RequestBody List<EGroupNoti> req) {
        groupNotiAdminService.updateGroup(req);
        return ResponseEntity.noContent().build();
    }
}
