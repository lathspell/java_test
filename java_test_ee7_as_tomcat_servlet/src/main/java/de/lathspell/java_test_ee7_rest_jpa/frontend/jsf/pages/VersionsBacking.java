package de.lathspell.java_test_ee7_rest_jpa.frontend.jsf.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.extern.slf4j.Slf4j;

import de.lathspell.java_test_ee7_rest_jpa.backend.sql.ArticleDAO;

@Named
@RequestScoped
@Slf4j
public class VersionsBacking {

    @Inject
    private ArticleDAO articleDAO;
    
    public VersionsBacking() {
        log.info("ctor");
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
        log.info(""+ articleDAO);
    }

    public String getJsfVersion() throws Exception {
        Package p = FacesContext.getCurrentInstance().getClass().getPackage();
        return String.format("%s %s using %s %s %s",
                p.getSpecificationTitle(), p.getSpecificationVersion(),
                p.getImplementationVendor(), p.getImplementationTitle(), p.getImplementationVersion());
    }
    
    public String getJtaVersion() throws Exception {
        return null;
    }
}
