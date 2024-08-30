package com.shsoftvina.community.modules.admin.book;

import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.admin.book.model.req.AddBookAdminReq;
import com.shsoftvina.community.modules.admin.book.model.req.EditBookAdminReq;
import com.shsoftvina.community.modules.admin.book.model.res.BookAdminRes;
import com.shsoftvina.community.modules.admin.book.service.BookAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/books")
public class BookAdminApi {

    @Autowired
    private BookAdminService bookAdminService;

    @GetMapping
    public ResponseEntity<List<BookAdminRes>> findAll() {
        return ResponseEntity.ok(bookAdminService.findAllBookAdmin());
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@Valid @RequestBody AddBookAdminReq req) {
        bookAdminService.createBook(req);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editBook(@Valid @RequestBody EditBookAdminReq req,
                                         @PathVariable Long id) {
        if(!id.equals(req.getId())){
            throw new BadRequestAlertException(ErrorEnum.ID_NOT_FOUND);
        }
        bookAdminService.editBook(req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBooks(@RequestBody List<Long> ids) {
        bookAdminService.deleteBooks(ids);
        return ResponseEntity.noContent().build();
    }
}
