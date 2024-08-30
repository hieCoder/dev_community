package com.shsoftvina.community.modules.root.book.service;

import com.shsoftvina.community.domain.Book;

public interface BookService extends BookEntityService{

    void save(Book book);
}
