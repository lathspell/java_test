package de.lathspell.java_test_ee6_nbpfcrud.jsf.admin;

import de.lathspell.java_test_ee6_nbpfcrud.model.BookToBookstore;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "bookToBookstoreController")
@ViewScoped
public class BookToBookstoreController extends AbstractController<BookToBookstore> implements Serializable {

    @EJB
    private BookToBookstoreFacade ejbFacade;

    public BookToBookstoreController() {
        super(BookToBookstore.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
