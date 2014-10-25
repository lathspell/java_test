package de.lathspell.test.patternbuch.command;

import de.lathspell.test.patternbuch.command.befehle.LichtAnBefehl;
import de.lathspell.test.patternbuch.command.geräte.*;

public class EinfacheFernSteuerungTest {

    public static void main(String[] args) {
        EinfacheFernSteuerung fernsteuerung = new EinfacheFernSteuerung();
        Licht licht = new Licht();
        LichtAnBefehl lichtEin = new LichtAnBefehl(licht);

        fernsteuerung.setBefehl(lichtEin);
        fernsteuerung.knopfWurdeGedrückt();
    }
}
