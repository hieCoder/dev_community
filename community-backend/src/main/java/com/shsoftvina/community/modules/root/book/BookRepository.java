package com.shsoftvina.community.modules.root.book;

import com.shsoftvina.community.domain.Book;
import com.shsoftvina.community.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.id = :id and b.status = 'ACTIVATED'")
    Optional<Book> findById(Long id);

    @Query("select b from Book b where b.id in :ids and b.status = 'ACTIVATED'")
    List<Book> findByIdIn(List<Long> ids);
}
