package de.lathspell.test.ws.soap;


public class TDO {
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public TDO(String name) {
        this.name = name;
    }

    public TDO() {

    }
}
