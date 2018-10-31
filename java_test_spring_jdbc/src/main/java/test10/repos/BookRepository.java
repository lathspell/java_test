package test10.repos;

import common.model.Book;

public interface BookRepository {

    public Book findById(long id);

    public Book save(Book book);
}
