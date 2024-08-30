package com.shsoftvina.community.modules.subscribemail;

import com.shsoftvina.community.modules.subscribemail.service.SubscribeMailDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subcribe-mail")
public class SubscribeMailDevApi {

    @Autowired
    private SubscribeMailDevService subscribeMailDevService;

    @PostMapping("/{email}")
    public ResponseEntity<Void> subscribeMail(@PathVariable String email) {
        subscribeMailDevService.subscribeMail(email);
        return ResponseEntity.noContent().build();
    }
}
