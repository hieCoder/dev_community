package com.shsoftvina.community.modules.admin.example;

import com.shsoftvina.community.modules.admin.example.model.res.ExampleAdminRes;
import com.shsoftvina.community.modules.admin.example.service.ExampleAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/examples")
public class ExampleAdminApi {

    @Autowired
    private ExampleAdminService exampleAdminService;

    @GetMapping
    public ResponseEntity<List<ExampleAdminRes>> findAllByCreatePost(@RequestParam(required = false) String keyword){
        return ResponseEntity.ok(exampleAdminService.findAllByCreatePost(keyword));
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> findAllName() {
        return ResponseEntity.ok(exampleAdminService.getAllName());
    }
}
