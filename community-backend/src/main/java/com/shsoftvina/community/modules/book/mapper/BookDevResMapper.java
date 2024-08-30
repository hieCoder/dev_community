package com.shsoftvina.community.modules.book.mapper;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.book.model.BookDevRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookDevResMapper extends EntityMapper<BookDevRes, Book> {
}
