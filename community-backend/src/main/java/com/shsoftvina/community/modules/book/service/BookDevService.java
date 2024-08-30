package com.shsoftvina.community.modules.book.service;

import com.shsoftvina.community.modules.book.model.BookDevRes;
import com.shsoftvina.community.modules.root.book.service.BookService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDevService extends BookService {

    List<BookDevRes> getListOutstandingBook(Pageable pageable);
}
