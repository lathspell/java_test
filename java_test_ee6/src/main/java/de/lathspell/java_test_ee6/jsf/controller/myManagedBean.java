package de.lathspell.java_test_ee6.jsf.controller;

import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class myManagedBean {

    private String myString = "Hello World!";
    private Date myDate = Calendar.getInstance().getTime();

    /** Creates a new instance of myManagedBean */
    public myManagedBean() {
    }

    public String getMyString() {
        return "getMyString() says: " + myString;
    }

    public Date getMyDate() {
        return myDate;
    }

    public void setMyDate(Date myDate) {
        this.myDate = myDate;
    }
}
