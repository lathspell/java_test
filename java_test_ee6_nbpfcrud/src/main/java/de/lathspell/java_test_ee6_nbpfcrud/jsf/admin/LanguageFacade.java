package de.lathspell.java_test_ee6_nbpfcrud.jsf.admin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.lathspell.java_test_ee6_nbpfcrud.model.Language;


@Stateless
public class LanguageFacade extends AbstractFacade<Language> {
    @PersistenceContext(unitName = "java_test_ee6_nbpfcrud_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LanguageFacade() {
        super(Language.class);
    }

}
