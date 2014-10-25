package de.lathspell.java_test_ee7_jsf.phases;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSF Backing Bean for /phases/phases.xhtml.
 */
@Named("phasesBacking")
@RequestScoped
public class PhasesBacking {

    private static final Logger log = LoggerFactory.getLogger(PhasesBacking.class);

    public PhasesBacking() {
        log.debug("Using " + getJsfVersion());
        log.debug("#### constructor ####");
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("#### postConstruct ####");
    }

    /**
     * Called by f:viewAction.
     *
     * @return Name of XHTML file to show or NULL to stay on current one.
     */
    public String processViewAction() {
        log.debug("**** ViewAction ****");

        return null;
    }

    /**
     * Called by f:viewAction.
     *
     * @param arg Parameter that from XHTML.
     * @return Name of XHTML file to show or NULL to stay on current one.
     */
    public String processViewAction(String arg) {
        log.debug("**** ViewAction({}) ****", arg);

        return null;
    }

    /**
     * Called by f:event.
     *
     * @param ev
     */
    public void processEvent(ComponentSystemEvent ev) {
        log.debug("**** Event {} ****", ev.getClass().getSimpleName());
    }

    public void processElMethod(String where) {
        log.debug("**** EL Method in {} ****", where);
    }

    public String getJsfVersion() {
        Package p = FacesContext.class.getPackage();
        return String.format("%s %s using %s %s %s",
                p.getSpecificationTitle(), p.getSpecificationVersion(),
                p.getImplementationVendor(), p.getImplementationTitle(), p.getImplementationVersion());
    }
}
