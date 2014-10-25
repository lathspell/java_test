package de.lathspell.test.patternbuch.command.befehle;

import de.lathspell.test.patternbuch.command.geräte.Licht;

public class LichtAnBefehl implements Befehl {

    Licht licht;

    public LichtAnBefehl(Licht licht) {
        this.licht = licht;
    }

    @Override
    public void ausführen() {
        licht.ein();
    }

    @Override
    public void rückgängig() {
        licht.aus();
    }
}
