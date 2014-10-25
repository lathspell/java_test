package de.lathspell.java_test_ee7_gfversion;

import java.util.Calendar;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named("MyBean")
public class MyBean {

    private String date;

    public MyBean() {
        date = "The current date is : " + Calendar.getInstance().getTime().toString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
