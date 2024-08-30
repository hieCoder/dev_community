package com.shsoftvina.community.modules.changelog;

import com.shsoftvina.community.modules.changelog.service.ChangelogDevService;
import com.shsoftvina.community.modules.changelog.model.res.ChangelogDevRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/changelogs")
public class ChangelogDevApi {

    @Autowired
    private ChangelogDevService changelogDevService;

    @GetMapping
    public ResponseEntity<List<ChangelogDevRes>> getChangelog(@RequestParam Long componentId){
        return ResponseEntity.ok(changelogDevService.getChangelog(componentId));
    }
}
