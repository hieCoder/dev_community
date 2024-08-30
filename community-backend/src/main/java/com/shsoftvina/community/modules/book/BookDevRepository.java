package com.shsoftvina.community.modules.book;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.modules.root.book.BookRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookDevRepository extends BookRepository {

    @Query("select b from Book b where b.status = 'ACTIVATED' order by b.createdDate desc")
    List<Book> findAllBookDev(Pageable pageable);
}
