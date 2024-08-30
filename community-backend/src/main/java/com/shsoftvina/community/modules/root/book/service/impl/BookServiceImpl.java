package com.shsoftvina.community.modules.root.book.service.impl;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.root.book.BookRepository;
import com.shsoftvina.community.modules.root.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Primary
public class BookServiceImpl extends BookEntityServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    protected void deleteBooks(List<Long> ids){
        bookRepository.saveAll(bookRepository.findByIdIn(ids).stream().peek(b -> b.setStatus(EStatus.DELETED)).toList());
    }
}
