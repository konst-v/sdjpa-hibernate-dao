package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;

import java.util.List;

/**
 * Created by jt on 8/22/21.
 */
public interface BookDao {
    List<Book> findAll();

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book findByISBN(String isbn);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    Book findBookByTitleCriteria(String title);

    Book findBookByTitleNative(String title);
}
