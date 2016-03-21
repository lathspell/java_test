package de.lathspell.test.patternbuch.command.geräte;

public class StereoAnlage {

    public void ein() {
        System.out.println(this + " ein");
    }

    public void setCD() {
        System.out.println(this + " setCD");
    }

    public void setLautstärke(int i) {
        System.out.println(this + " setLautstärke(" + i + ")");
    }

    public void aus() {
        System.out.println(this + " aus");
    }
}
