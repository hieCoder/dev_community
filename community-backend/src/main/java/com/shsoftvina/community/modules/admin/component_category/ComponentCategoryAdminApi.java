package com.shsoftvina.community.modules.admin.component_category;

import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryAddAdminReq;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryEditAdminReq;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryAdminRes;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryDetailAdminRes;
import com.shsoftvina.community.modules.admin.component_category.service.ComponentCategoryAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/component-categories")
public class ComponentCategoryAdminApi {

    @Autowired
    private ComponentCategoryAdminService componentCategoryAdminService;

    @PostMapping
    public ResponseEntity<CategoryAdminRes> createCategory(@Valid @RequestBody CategoryAddAdminReq req){
        return ResponseEntity.ok(componentCategoryAdminService.createCategory(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editCategory(@Valid @RequestBody CategoryEditAdminReq req,
                                             @PathVariable Long id){
        if(!id.equals(req.getId())){
            throw new BadRequestAlertException(ErrorEnum.ID_NOT_FOUND);
        }
        componentCategoryAdminService.editCategory(req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        componentCategoryAdminService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetailAdminRes> findDetail(@PathVariable Long id) {
        return ResponseEntity.ok(componentCategoryAdminService.findDetail(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryAdminRes>> findAllByAdmin() {
        return ResponseEntity.ok(componentCategoryAdminService.findAllByAdmin());
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> findAllName() {
        return ResponseEntity.ok(componentCategoryAdminService.getAllName());
    }
}
