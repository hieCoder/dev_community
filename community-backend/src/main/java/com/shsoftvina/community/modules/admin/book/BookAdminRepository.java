package com.shsoftvina.community.modules.admin.book;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.modules.root.book.BookRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookAdminRepository extends BookRepository {

    @Query("select b from Book b where b.status = 'ACTIVATED' order by b.createdDate desc")
    List<Book> findAllBookAdmin();
}
