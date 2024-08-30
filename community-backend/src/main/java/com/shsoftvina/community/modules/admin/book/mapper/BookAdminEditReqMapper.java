package com.shsoftvina.community.modules.admin.book.mapper;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.book.model.req.AddBookAdminReq;
import com.shsoftvina.community.modules.admin.book.model.req.EditBookAdminReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookAdminEditReqMapper extends EntityMapper<EditBookAdminReq, Book> {
}
