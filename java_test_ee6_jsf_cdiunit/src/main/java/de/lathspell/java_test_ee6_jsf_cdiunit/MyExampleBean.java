package de.lathspell.java_test_ee6_jsf_cdiunit;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MyExampleBean {

    private String name;
    @Inject
    MyConverter myConverter;

    public MyExampleBean() {
        name = "Tim";
    }

    public String getName() {
        return myConverter.convert(name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
