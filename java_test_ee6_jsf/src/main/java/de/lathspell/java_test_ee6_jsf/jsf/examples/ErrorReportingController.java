package de.lathspell.java_test_ee6_jsf.jsf.examples;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

/**
 * Controller class for examples/error_reporting.xhtml.
 *
 * The class will found regardless of its package name due to the
 *
 * @Named
 * annotation!
 */
@Named
@RequestScoped
public class ErrorReportingController {

    private static final Logger log = LoggerFactory.getLogger(ErrorReportingController.class);

    private String myValue = "foo";

    private List<String> myList = new ArrayList<>();

    public ErrorReportingController() {
        myList.add("one");
        myList.add("two");
        myList.add("three");
    }

    public String submit() {
        try {
            throw new Exception("Something is broken!");
        } catch (Exception e) {
            // addMessage(null, ...) => global message
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, e.getMessage(), ""));
            return null;
        }
    }

    public String getMyValue() {
        return myValue;
    }

    public void setMyValue(String myValue) {
        this.myValue = myValue;
    }

    public List<String> getMyList() {
        return myList;
    }

    /**
     * For PrimeFaces DataTable AJAX handling.
     */
    public String onCellEdit() {
        try {
            throw new Exception("Something not right with this value!");
        } catch (Exception e) {
            log.error("during onCellEditing", e);
            FacesContext.getCurrentInstance().addMessage(":primefaces_error_reporting_form:messagesForDataTable", new FacesMessage(SEVERITY_ERROR, e.getMessage(), ""));
            return null;
        }
    }

    public String submit4(String item) {
        if (item == null) {
            log.error("item is null!");
        } else {
            log.error("item: >>" + item + "<<" + item.length());
        }
        return "";
    }

    public String submit3() throws Exception {
        try {
            throw new Exception("This error in form3 is not handled.");
        } catch (Exception e) {
            log.error("submit3: " + e);
            // return null; - With this the button would seem to have no effect.
            throw e; // JSF will show an "Error 500" page with the exception message!
        }
    }
}
