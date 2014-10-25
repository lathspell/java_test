package de.lathspell.test.sql;

/**
 * Transferobjekt für DbUtils BeanListMapper.
 *
 * Musste leider public sein und zumindest setter haben.
 */
public class JunitRow {

    public Integer i;
    public String s;
    public Integer hasNoSetter;

    public JunitRow() {
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setS(String s) {
        this.s = s;
    }
}
