package com.shsoftvina.community.modules.admin.book.service.impl;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.admin.book.BookAdminRepository;
import com.shsoftvina.community.modules.admin.book.mapper.BookAdminAddReqMapper;
import com.shsoftvina.community.modules.admin.book.mapper.BookAdminEditReqMapper;
import com.shsoftvina.community.modules.admin.book.mapper.BookAdminResMapper;
import com.shsoftvina.community.modules.admin.book.model.req.AddBookAdminReq;
import com.shsoftvina.community.modules.admin.book.model.req.EditBookAdminReq;
import com.shsoftvina.community.modules.admin.book.model.res.BookAdminRes;
import com.shsoftvina.community.modules.admin.book.service.BookAdminService;
import com.shsoftvina.community.modules.root.book.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookAdminServiceImpl extends BookServiceImpl implements BookAdminService {

    @Autowired
    private BookAdminRepository bookAdminRepository;

    @Autowired
    private BookAdminResMapper bookAdminResMapper;

    @Autowired
    private BookAdminAddReqMapper bookAdminAddReqMapper;

    @Autowired
    private BookAdminEditReqMapper bookAdminEditReqMapper;

    @Override
    public List<BookAdminRes> findAllBookAdmin() {
        return bookAdminResMapper.toDto(bookAdminRepository.findAllBookAdmin());
    }

    @Override
    public void createBook(AddBookAdminReq req) {
        Book book = bookAdminAddReqMapper.toEntity(req);
        book.setStatus(EStatus.ACTIVATED);
        bookAdminRepository.save(book);
    }

    @Override
    public void editBook(EditBookAdminReq req) {
        Long idReq = req.getId();
        Book book = super.findById(idReq);
        bookAdminEditReqMapper.partialUpdate(book, req);

        bookAdminRepository.save(book);
    }

    @Override
    public void deleteBooks(List<Long> ids) {
        super.deleteBooks(ids);
    }
}