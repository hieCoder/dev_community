package com.shsoftvina.community.modules.root.book.service.impl;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.root.book.BookRepository;
import com.shsoftvina.community.modules.root.book.service.BookEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookEntityServiceImpl implements BookEntityService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.BOOK_NOT_FOUND));
    }
}
