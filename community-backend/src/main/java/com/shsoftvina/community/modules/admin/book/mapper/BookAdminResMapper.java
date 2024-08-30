package com.shsoftvina.community.modules.admin.book.mapper;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.book.model.res.BookAdminRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookAdminResMapper extends EntityMapper<BookAdminRes, Book> {
}
