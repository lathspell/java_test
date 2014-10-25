package de.lathspell.test.patternbuch.command;

import de.lathspell.test.patternbuch.command.befehle.*;

/** Beispiel für das Command-Muster. */
public class FernSteuerung {

    Befehl[] anBefehle;
    Befehl[] ausBefehle;
    Befehl rückgängigBefehl;

    public FernSteuerung() {
        anBefehle = new Befehl[7];
        ausBefehle = new Befehl[7];

        Befehl keinBefehl = new KeinBefehl();
        for (int i = 0; i < 7; i++) {
            anBefehle[i] = keinBefehl;
            ausBefehle[i] = keinBefehl;
        }
        rückgängigBefehl = keinBefehl;
    }

    public void setBefehl(int platz, Befehl anBefehl, Befehl ausBefehl) {
        anBefehle[platz] = anBefehl;
        ausBefehle[platz] = ausBefehl;
    }

    public void anKnopfWurdeGedrückt(int platz) {
        anBefehle[platz].ausführen();
        rückgängigBefehl = anBefehle[platz];
    }

    public void ausKnopfWurdeGedrückt(int platz) {
        ausBefehle[platz].ausführen();
        rückgängigBefehl = ausBefehle[platz];
    }

    public void rückgängigKnopfWurdeGedrückt() {
        rückgängigBefehl.rückgängig();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n----- Fernsteuerung ----\n");
        for (int i = 0; i < anBefehle.length; i++) {
            sb.append("[P " + i + "] " + anBefehle[i].getClass().getName() + "    " + ausBefehle[i].getClass().getName() + "\n");
        }
        sb.append("[P Rück] " + rückgängigBefehl.getClass().getName() + "\n");
        return sb.toString();
    }
}
