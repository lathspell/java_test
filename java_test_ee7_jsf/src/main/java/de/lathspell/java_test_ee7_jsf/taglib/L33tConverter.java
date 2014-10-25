package de.lathspell.java_test_ee7_jsf.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.CharacterConverter;
import javax.faces.convert.FacesConverter;

/**
 * Converts text to "L33tspeak".
 *
 * Instead of extending CharacterConverted this class could also just implement
 * javax.faces.Converter. CharacterConverter just brings the benefit of a
 * getAsString() method with NPE checks.
 */
@FacesConverter("de.lathspell.L33tConverter")
public class L33tConverter extends CharacterConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value.replaceAll("e", "3").replaceAll("i", "1").replaceAll("a", "@");
    }

}
