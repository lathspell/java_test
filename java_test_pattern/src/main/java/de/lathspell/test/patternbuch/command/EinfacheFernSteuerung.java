package de.lathspell.test.patternbuch.command;

import de.lathspell.test.patternbuch.command.befehle.Befehl;

/** Beispiel für das Command-Muster. */
public class EinfacheFernSteuerung {

    Befehl platz;

    public EinfacheFernSteuerung() {
    }

    public void setBefehl(Befehl befehl) {
        platz = befehl;
    }

    public void knopfWurdeGedrückt() {
        platz.ausführen();
    }
}
