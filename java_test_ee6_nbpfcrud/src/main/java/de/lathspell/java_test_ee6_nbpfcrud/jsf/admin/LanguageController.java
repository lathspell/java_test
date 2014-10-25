package de.lathspell.java_test_ee6_nbpfcrud.jsf.admin;

import de.lathspell.java_test_ee6_nbpfcrud.model.Language;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "languageController")
@ViewScoped
public class LanguageController extends AbstractController<Language> implements Serializable {

    @EJB
    private LanguageFacade ejbFacade;

    public LanguageController() {
        super(Language.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
