package de.lathspell.java_test_ee6.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class MyEjbBean {

    public int addNumbers(int numberA, int numberB) {
        return numberA + numberB;
    }

}
