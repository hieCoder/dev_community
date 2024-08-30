package com.shsoftvina.community.modules.admin.book.service;

import com.shsoftvina.community.modules.admin.book.model.req.AddBookAdminReq;
import com.shsoftvina.community.modules.admin.book.model.req.EditBookAdminReq;
import com.shsoftvina.community.modules.admin.book.model.res.BookAdminRes;
import com.shsoftvina.community.modules.root.book.service.BookService;

import java.util.List;

public interface BookAdminService extends BookService {

    List<BookAdminRes> findAllBookAdmin();
    void createBook(AddBookAdminReq req);
    void editBook(EditBookAdminReq req);
    void deleteBooks(List<Long> ids);
}
