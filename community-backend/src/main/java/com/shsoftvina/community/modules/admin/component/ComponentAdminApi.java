package com.shsoftvina.community.modules.admin.component;

import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.admin.component.model.req.CreateComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.req.EditComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.req.MoveComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.res.ComponentDetailAdminRes;
import com.shsoftvina.community.modules.admin.component.model.res.ListComponentAdminRes;
import com.shsoftvina.community.modules.admin.component.service.ComponentAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/components")
public class ComponentAdminApi {

    @Autowired
    private ComponentAdminService componentAdminService;

    @GetMapping
    public ResponseEntity<ListComponentAdminRes> findAll() {
        return ResponseEntity.ok(componentAdminService.getListComponentAdmin());
    }

    @PostMapping
    public ResponseEntity<Void> createComponent(@Valid @RequestBody CreateComponentAdminReq req){
        componentAdminService.createComponent(req);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editComponent(@Valid @RequestBody EditComponentAdminReq req,
                                              @PathVariable Long id){
        if(!req.getId().equals(id)){
            throw new BadRequestAlertException(ErrorEnum.ID_NOT_FOUND);
        }
        componentAdminService.editComponent(req);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentDetailAdminRes> getDetail(@PathVariable Long id){
        return ResponseEntity.ok(componentAdminService.getDetail(id));
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> findAllName() {
        return ResponseEntity.ok(componentAdminService.getAllName());
    }

    @PutMapping("/move/{id}")
    public ResponseEntity<Void> moveComponent(@Valid @RequestBody MoveComponentAdminReq req,
                                              @PathVariable Long id){
        if(!req.getId().equals(id)){
            throw new BadRequestAlertException(ErrorEnum.ID_NOT_FOUND);
        }
        componentAdminService.moveComponent(req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        componentAdminService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
