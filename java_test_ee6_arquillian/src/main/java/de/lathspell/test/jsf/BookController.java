package de.lathspell.test.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.lathspell.test.model.Book;

@ManagedBean
@RequestScoped
public class BookController {

    private Book book;

    public BookController() {
        book = new Book();
        book.setAuthor("Jules Verne");
    }

    public String getAuthor() {
        return book.getAuthor();
    }

    public void setAuthor(String author) {
        book.setAuthor(author);
    }

}
