package com.shsoftvina.community.modules.component;

import com.shsoftvina.community.modules.component.model.res.ComponentDetailDevRes;
import com.shsoftvina.community.modules.component.model.res.ListComponentDevRes;
import com.shsoftvina.community.modules.component.service.ComponentDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/components")
public class ComponentDevApi {

    @Autowired
    private ComponentDevService componentDevService;

    @GetMapping
    public ResponseEntity<ListComponentDevRes> findAll() {
        return ResponseEntity.ok(componentDevService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentDetailDevRes> getDetail(@RequestHeader String ipClient, @PathVariable Long id){
        return ResponseEntity.ok(componentDevService.getDetail(ipClient, id));
    }

    @PutMapping("/{id}/sharing")
    public ResponseEntity<Void> updateSharing(@PathVariable Long id) {
        componentDevService.updateSharing(id);
        return ResponseEntity.noContent().build();
    }
}
