package de.lathspell.java_test_ee7_jsf.taglib;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesValidator("de.lathspell.OddNumberValidator")
public class OddNumberValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(OddNumberValidator.class);

    private boolean wantOdd = true;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Integer i = (Integer) value;
        int wanted = wantOdd ? 1 : 0;
        if (i != null && ((i % 2) != wanted)) {
            log.warn("Validation failed for: " + value);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "No odd value!", null));
        }
    }

    public boolean isWantOdd() {
        return wantOdd;
    }

    public void setWantOdd(boolean wantOdd) {
        this.wantOdd = wantOdd;
    }

}
