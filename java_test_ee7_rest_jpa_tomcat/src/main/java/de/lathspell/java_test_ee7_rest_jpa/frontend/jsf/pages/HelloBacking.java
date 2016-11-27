package de.lathspell.java_test_ee7_rest_jpa.frontend.jsf.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Named
@RequestScoped
@Slf4j
public class HelloBacking {

    public HelloBacking() {
        log.info("ctor");
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
    }

    public String getVersion() throws Exception {
        Package p = FacesContext.class.getPackage();
        if (p.getSpecificationTitle() == null) {
            p = Package.getPackage("com.sun.faces");
        }
        return String.format("%s %s using Implementation %s %s %s",
                p.getSpecificationTitle(), p.getSpecificationVersion(),
                p.getImplementationVendor(), p.getImplementationTitle(), p.getImplementationVersion());
    }
}
