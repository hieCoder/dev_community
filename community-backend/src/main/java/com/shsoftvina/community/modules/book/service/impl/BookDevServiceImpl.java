package com.shsoftvina.community.modules.book.service.impl;

import com.shsoftvina.community.modules.book.BookDevRepository;
import com.shsoftvina.community.modules.book.mapper.BookDevResMapper;
import com.shsoftvina.community.modules.book.model.BookDevRes;
import com.shsoftvina.community.modules.book.service.BookDevService;
import com.shsoftvina.community.modules.root.book.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookDevServiceImpl extends BookServiceImpl implements BookDevService {

    @Autowired
    private BookDevRepository bookDevRepository;

    @Autowired
    private BookDevResMapper bookDevResMapper;

    @Override
    public List<BookDevRes> getListOutstandingBook(Pageable pageable) {
        return bookDevResMapper.toDto(bookDevRepository.findAllBookDev(pageable));
    }
}
