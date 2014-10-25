package de.lathspell.java_test_ee6_nbpfcrud.jsf.admin;

import de.lathspell.java_test_ee6_nbpfcrud.model.Bookstore;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "bookstoreController")
@ViewScoped
public class BookstoreController extends AbstractController<Bookstore> implements Serializable {

    @EJB
    private BookstoreFacade ejbFacade;

    public BookstoreController() {
        super(Bookstore.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
