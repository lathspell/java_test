package de.lathspell.java_test_ee6_nbpfcrud.jsf.admin;

import de.lathspell.java_test_ee6_nbpfcrud.model.Author;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "authorController")
@ViewScoped
public class AuthorController extends AbstractController<Author> implements Serializable {

    @EJB
    private AuthorFacade ejbFacade;

    public AuthorController() {
        super(Author.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
