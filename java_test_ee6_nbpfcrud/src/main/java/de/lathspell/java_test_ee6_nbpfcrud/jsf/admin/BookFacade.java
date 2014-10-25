package de.lathspell.java_test_ee6_nbpfcrud.jsf.admin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.lathspell.java_test_ee6_nbpfcrud.model.Book;


@Stateless
public class BookFacade extends AbstractFacade<Book> {
    @PersistenceContext(unitName = "java_test_ee6_nbpfcrud_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

}
