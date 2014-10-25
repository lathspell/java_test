package de.lathspell.test.patternbuch.command.befehle;

import de.lathspell.test.patternbuch.command.geräte.Licht;

public class LichtAusBefehl implements Befehl {

    Licht licht;

    public LichtAusBefehl(Licht licht) {
        this.licht = licht;
    }

    @Override
    public void ausführen() {
        licht.aus();
    }

    @Override
    public void rückgängig() {
        licht.ein();
    }
}
